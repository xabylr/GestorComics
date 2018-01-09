
package gestorComics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.imageio.ImageIO;

import org.sqlite.SQLiteException;

import excepciones.ExcepcionBD;

public class BD implements IBD{
	private static final String RES_FOLDER = "src/res";
	private static final String FILE_BD = "dibujante.db";
	private static final String FILE_DDL = "generadorBD.ddl";
	private static final String CARPETA_PROYECTO = "GestorComics";
	private static final String STR_SQLITE = "jdbc:sqlite:";
	private static String URL_BD = getUrlBDGenerator() + "/"+ FILE_BD;
	private static File archivoDDL;
	static{URL_BD = URL_BD.replace("\\", "/");
	archivoDDL = new File(RES_FOLDER+"/"+FILE_DDL);
	}

	private Connection con ;
	private File archivoBD;
	
	private int ultimoIdComic;
	private int ultimoIdVineta;
	
	public BD(){
		new File(BD.getUrlBDGenerator()).mkdirs();
		archivoBD = new File(URL_BD);
		boolean existia = archivoBD.exists();
		Conectar();
		
		if(!existia){
			System.out.println("Base de datos no encontrada: creando una nueva.");
			GenerarBaseDatos();
			
		}
		
        System.out.println("Se ha establecido la conexión con el archivo BD");
        
        ultimoIdComic = getUltimoIdComic();
        ultimoIdVineta = getUltimoIdVineta();    
			
	}
	
	private void Conectar() {
		PreparedStatement psmnt = null;
		try
		{
        // create a connection to the database
			con = DriverManager.getConnection(STR_SQLITE+URL_BD);
			psmnt = con.prepareStatement("PRAGMA FOREIGN_KEY=ON");
			psmnt.executeUpdate();
		}
		catch (SQLException ex)
		{
			throw new Error("Error al Conectar con la base de datos." + ex.getMessage());
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
	}
	
	protected void finalize () 
	{
		try
		{
			if (con!=null)  con.close();
		}
		catch (SQLException ex)
		{
			throw new Error("Error al Cerrar la Conexión." + ex.getMessage());
		}
    }
		
	
	private void GenerarBaseDatos() {
		Statement stmt =null;
		try {
			archivoDDL.setReadable(true);
			stmt = con.createStatement();
			
			Scanner sc = new Scanner(new FileReader(archivoDDL));
			sc.useDelimiter(";");
			
			while(sc.hasNext()){
				String next = sc.next();
				stmt.executeUpdate(next);
			}

			sc.close();
		}catch(Exception e) {
			throw new ExcepcionBD("Error al general la BD", e);
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
	}
	
	private static String getUrlBDGenerator() {
		String OS = System.getProperty("os.name").toLowerCase();
		String home;
		
		if(OS.indexOf("win") >= 0) {
			home = System.getProperty("user.home") + "/AppData/Roaming/";
		} else {
			home = System.getProperty("user.home") + "/."; 
		}
		
		return home + CARPETA_PROYECTO;
		
	}
	

	@Override
	public void insertarVineta(Vineta vineta, int comic) throws ExcepcionBD {
		//Convertimos la imagen a un archivo png en memoria ( stream de array de bytes y a un inputstream ) para subir
		
		int enlaces = getEnlaces(vineta.getID()); //si la viñeta tiene id =-1 hay 0 enlaces
		
		PreparedStatement psmnt = null;
	try {
	
	//Añadimos tupla a la lista de viñetas o aumentamos sus enlaces
		
		if (enlaces == 0) { //viñeta nueva, insertar imagen y asignar ID
			
				if(vineta.getID()==-1) {
					vineta.setID(++ultimoIdVineta);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write((BufferedImage)vineta.getImagen(), "png", baos);
					InputStream is = new ByteArrayInputStream(baos.toByteArray());

					//creamos la sentencia SQL para subir la imagen	
						psmnt = con.prepareStatement(
							"INSERT INTO VINETA (NOMBRE, ID, IMAGEN, ENLACES) VALUES (?,?,?,?)");
						psmnt.setString(1, vineta.getNombre());
						psmnt.setInt(2, vineta.getID());
						psmnt.setBinaryStream(3, is, baos.size() );
						psmnt.setInt(4, 1); //un enlace
							
					psmnt.executeUpdate(); 
				}
			
		}else {
			psmnt = con.prepareStatement(
					"UPDATE VINETA SET ENLACES=? WHERE ID=?");
			psmnt.setInt(1, enlaces+1);
			psmnt.setInt(2, vineta.getID());
			psmnt.executeUpdate();
		}
		
		psmnt.close(); //lo cerramos porque vamos a cambiar de objeto
		
		//Añadimos al cómic la viñeta en cuestión
	
			psmnt = con.prepareStatement(
					"INSERT INTO COMIC_VINETA (VINETA_ID, COMIC_ID) VALUES(?,?)");
			psmnt.setInt(1, vineta.getID());
			psmnt.setInt(2, comic);
			
			psmnt.executeUpdate();

	
}catch (SQLException | IOException e) {
	throw new ExcepcionBD("Error al insertar  "+vineta, e);
}finally {
	try {
		psmnt.close();
	} catch (SQLException e) {
		throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
	} catch (NullPointerException e){
		//(?)
	}
}
	
	}
	
	@Override
	public int getEnlaces(int vineta) {
		PreparedStatement psmnt = null;
		int enlaces = -1;
		if (vineta == -1) enlaces = 0;
		else {
			try {
				psmnt = con.prepareStatement(
					"SELECT ENLACES FROM VINETA WHERE ID = ?");
				if(psmnt.getFetchSize()==0) enlaces=0;
				else {
					psmnt.setInt(1, vineta);
					enlaces = psmnt.executeQuery().getInt(1);
				}
		
			}catch (SQLException e) {
				throw new ExcepcionBD("Error al obtener núm de enlaces("+e.getMessage()+")");
			}
		}
		
		return enlaces;
		
	}
	
	@Override
	public void insertarComic(Comic comic) throws ExcepcionBD  {
		PreparedStatement psmnt = null;
try {
	
		if(comic.getID()==-1)comic.setID(++ultimoIdComic);
	

			psmnt = con.prepareStatement(
			"INSERT INTO COMIC (ID, NOMBRE, PORTADA) VALUES (?,?,?)");
			
			psmnt.setInt(1, comic.getID() );
			psmnt.setString(2, comic.getNombre() );
			if(comic.getPortada()!=null) {
				psmnt.setInt(3, comic.getPortada().getID());
			}
			psmnt.executeUpdate();
	
		} catch (ExcepcionBD | SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al insertar "+comic, e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
	}

	@Override
	public List<Comic> getComics() throws ExcepcionBD{
		//lista a devolver de cómics
		List<Comic> resultado = new ArrayList<>();
		PreparedStatement psmnt=null;
		try {
		//obtener cómics con su viñeta de portada
		psmnt = con.prepareStatement("SELECT ID, NOMBRE, PORTADA FROM COMIC");
		ResultSet rs = psmnt.executeQuery();
		
		while(rs.next() ) {
			int id = rs.getInt("ID");
			String nombre = rs.getString("NOMBRE");
			int IDportada = rs.getInt("PORTADA");
			if (rs.wasNull()) IDportada = -1;

			Comic c = new Comic(nombre);
			c.setID(id);
			
			c.setPortada(getVineta(IDportada) );
			
			c.conectar(this);
			
			resultado.add(c);
		}
		
		}catch (SQLException e) {
			throw new ExcepcionBD("Error en la base de datos", e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
			return resultado;	
	}
	
	
	
	private Vineta getVineta(int id) throws ExcepcionBD  {
		if (id == -1) return null;
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement("SELECT IMAGEN, NOMBRE, ID"
					+ " FROM VINETA WHERE ID="+id);
			ResultSet rs = psmnt.executeQuery();
			
			if(rs.next()) {
				Image imagen=ImageIO.read(rs.getBinaryStream("IMAGEN"));
				 
				String nombre = rs.getString("NOMBRE");

				Vineta v = new Vineta(imagen, nombre);
				v.setID(id);
				
				return v;
			} else return null;
			
			
		} catch (SQLException | IOException e) {
			throw new ExcepcionBD("Error al encontrar la viñeta con id "+id, e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
	}
/*
	@Override
	public Vineta getPortada(Comic comic) {
		SELECT * FROM COMIC, COMIC_VINETA WHERE COMIC.PORTADA = ?
	}*/

	@Override
	public List<Vineta> getVinetas(int comic) throws ExcepcionBD  {
		
		List<Vineta> resultado = new ArrayList<>();
		PreparedStatement psmnt = null;
		try {
		//SELECT * FROM VINETA, COMIC_VINETA WHERE COMIC_VINETA.COMIC_ID = ?;
		psmnt = con.prepareStatement(
				"SELECT V.IMAGEN, V.NOMBRE, V.ID FROM VINETA V, COMIC_VINETA CV"
				+ " WHERE CV.VINETA_ID=V.ID AND  CV.COMIC_ID = ?");
		psmnt.setInt(1, comic);
		ResultSet rs = psmnt.executeQuery();
		while(rs.next()) {
			Image imagen = ImageIO.read(rs.getBinaryStream("IMAGEN"));
			 
			String nombre = rs.getString("NOMBRE");
			int id = rs.getInt("ID");
			
			Vineta v = new Vineta(imagen, nombre);
			v.setID(id);
			
			resultado.add(v);
		} 
				
				
		} catch (IOException | SQLException e) {
			throw new ExcepcionBD("Error al obtener las viñetas del cómic "+comic, e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}		
		
		return resultado;
	}

	@Override
	public int getUltimoIdComic() {
		int resultado=-1;
		PreparedStatement psmnt = null;
	try {
			psmnt = con.prepareStatement(
					"SELECT MAX (ID) FROM COMIC");
			ResultSet rs = psmnt.executeQuery();
			if(rs.next()) resultado = rs.getInt(1);
			
	} catch (SQLException e) {
		throw new ExcepcionBD("Error al obtener el último ID de la BD", e);
	}finally {
		try {
			psmnt.close();
		} catch (SQLException e) {
			throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
		}
	}
		
		return resultado;
	}
	
	@Override
	public int getUltimoIdVineta() {
		int resultado=-1;
		PreparedStatement psmnt = null;
	try {
			psmnt = con.prepareStatement(
					"SELECT MAX (ID) FROM VINETA");
			ResultSet rs = psmnt.executeQuery();
			if(rs.next()) resultado = rs.getInt(1);
			
	} catch (SQLException e) {
		e.printStackTrace();
		throw new ExcepcionBD("Error al obtener el último ID de la BD",e);
	}finally {
		try {
			psmnt.close();
		} catch (SQLException e) {
			throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
		}
	}
		
		return resultado;
	}
	

	@Override
	public void setPortadaComic(Vineta v, Comic c) {
		PreparedStatement psmnt = null;
		try {
			psmnt = con.prepareStatement(
					"UPDATE COMIC SET PORTADA=? WHERE ID=?");
			if(v!=null)psmnt.setInt(1, v.getID());
			else psmnt.setNull(1, java.sql.Types.NULL);
			
			psmnt.setInt(2, c.getID());
			
			psmnt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al poner portada (ID: "+v+") en comic (ID: "+c+") ("+e.getMessage()+")");
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
	}

	@Override
	public void renombrarVineta(Vineta v, String n) throws ExcepcionBD {
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement(
					"UPDATE VINETA SET NOMBRE=? WHERE ID=?");
			psmnt.setString(1, n);
			psmnt.setInt(2, v.getID());
			psmnt.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al renombrar viñeta (ID: "+v+") ("+e.getMessage()+")");
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
	}

	@Override
	public void renombrarComic(int c, String n) throws ExcepcionBD {
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement(
					"UPDATE COMIC SET NOMBRE = ? WHERE ID = ?");
			psmnt.setString(1, n);
			psmnt.setInt(2, c);
			psmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al renombrar el comic (ID: "+c+") ("+e.getMessage()+")");
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
	}

	/*
	 * Decrementar en uno el contador de enlaces y cuando sea 1, Borrar la tupla de la base de datos
	 * 
	 */
	@Override
	public void borrarVineta(int v, int c) throws ExcepcionBD {

		int enlaces = getEnlaces(v);
		
		PreparedStatement psmnt = null;
		try {
			
			if(enlaces==1) {
				psmnt = con.prepareStatement(
						"DELETE FROM VINETA WHERE ID = ?");
				psmnt.setInt(1, v);
				psmnt.execute();
			}else if(enlaces>=1) {
				psmnt = con.prepareStatement(
						"UPDATE VINETA SET ENLACES=? WHERE ID = ?");
				psmnt.setInt(1, enlaces-1);
				psmnt.setInt(2, v);
				psmnt.execute();
			}else throw new ExcepcionBD("Error de número de enlaces de viñeta con ID: "+v);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al borrar viñeta (ID: "+v+") ("+e.getMessage()+")");
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
	}

	@Override
	public void borrarComic(int c) throws ExcepcionBD {
		
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement(
					"DELETE FROM COMIC WHERE ID = ?");
			psmnt.setInt(1, c);
			psmnt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al borrar comic (ID: "+c+") ("+e.getMessage()+")");
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
	}

	@Override
	public Vineta getPortada(int c) throws ExcepcionBD {
		return null;
//		PreparedStatement psmnt = null;
//		try {
//			psmnt = con.prepareStatement(
//					"SELECT * FROM VINETA WHERE ID = (SELECT PORTADA FROM COMIC WHERE ID = "+c);
////			psmnt.setInt(1, c);
//			ResultSet rs = psmnt.executeQuery();
//
//			if(rs.next()) {
//				Image imagen = null;
//				try {
//					imagen = ImageIO.read(rs.getBinaryStream("IMAGEN"));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				 
//				String nombre = rs.getString("NOMBRE");
//				
//				int id = rs.getInt("ID");
//
//				Vineta v = new Vineta(imagen, nombre);
//				v.setID(id);
//				
//				return v;
//			} else return null;
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new ExcepcionBD("Error al borrar comic (ID: "+c+") ("+e.getMessage()+")");
//		}finally {
//		try {
//			psmnt.close();
//		} catch (SQLException e) {
//			throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
//		}
//	}
		
	}

	
	/*
	 * Inserta una lista de viñetas para un cómic dado (enlaces=1);
	 */
	@Override
	public void insertarVinetas(List<Vineta> vs, int c) {
		PreparedStatement psmnt = null;
		try {
			psmnt = con.prepareStatement(
					"INSERT INTO VINETA (ID,NOMBRE,ENLACES,IMAGEN) VALUES (?,?,?,?)");
			
			for(Vineta v : vs) {
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					ImageIO.write((BufferedImage)v.getImagen(), "png", baos);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				InputStream is = new ByteArrayInputStream(baos.toByteArray());
					
				
				try {
					psmnt.setInt(1, v.getID());
					psmnt.setString(2, v.getNombre());
					psmnt.setInt(3,1); //se considera que cada viñeta es solo de ese cómic
					psmnt.setBinaryStream(4, is, baos.size() );
					psmnt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
		
		
	}

	@Override
	public void actualizarImagenVineta(Vineta v) {

		PreparedStatement psmnt = null;
		try {
			psmnt = con.prepareStatement(
					"UPDATE VINETA SET IMAGEN = ? WHERE ID = ?");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write((BufferedImage)v.getImagen(), "png", baos);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			psmnt.setBinaryStream(1, is, baos.size() );
			psmnt.setInt(2, v.getID());
			psmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al renombrar el comic (ID: "+v.getID()+") ("+e.getMessage()+")");
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
	}

	/*
	 * El boceto se comporta como una viñeta en el programa
	 * 
	 */
	@Override
	public void insertarBoceto(Vineta boceto, Comic comic) {
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement(
					"INSERT INTO BOCETO (ID,COMIC_ID,IMAGEN) VALUES (?,?,?)");
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					ImageIO.write((BufferedImage)boceto.getImagen(), "png", baos);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				InputStream is = new ByteArrayInputStream(baos.toByteArray());
					
				
				try {
					psmnt.setInt(1, boceto.getID());
					psmnt.setString(2, boceto.getNombre());
					psmnt.setBinaryStream(3, is, baos.size() );
					psmnt.executeUpdate();
				} catch (SQLException e) {
					throw new ExcepcionBD(
							"Error al insertar boceto (ID: "+boceto.getID()+") "
									+ "("+e.getMessage()+")");
				}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
			
	}
	
	
	@Override
	public List<Vineta> getBocetos(int iD) {
		List<Vineta> resultado = new ArrayList<>();
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement(
					"SELECT * FROM BOCETO WHERE ID = ?");
			psmnt.setInt(1, iD);
			ResultSet rs = psmnt.executeQuery();
			if(rs.next()) {
				try {
					resultado.add(new Vineta(ImageIO.read(rs.getBinaryStream("IMAGEN"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	} catch (SQLException e) {
		e.printStackTrace();
		throw new ExcepcionBD("Error al obtener el último ID de la BD",e);
	}finally {
		try {
			psmnt.close();
		} catch (SQLException e) {
			throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
		}
	}
		
		return resultado;
	}

	@Override
	public void insertarAnotacion(Comic comic, Vineta vineta, Vineta boceto, String comentario) {
		System.out.println("INSERTANDO ANOTACION");
		PreparedStatement psmnt=null;
		try {
			psmnt = con.prepareStatement(
					"INSERT INTO ANOTACION (COMIC_ID, VINETA_ID, BOCETO_ID, TEXTO) VALUES (?, ?, ?, ?)");
			if(comic!=null)psmnt.setInt(1, comic.getID());
			else psmnt.setNull(1, java.sql.Types.NULL);
			
			if(vineta!=null)psmnt.setInt(2, vineta.getID());
			else psmnt.setNull(2, java.sql.Types.NULL);
			
			if(boceto != null)psmnt.setInt(3, boceto.ID);
			else psmnt.setNull(3, java.sql.Types.NULL);
			
			psmnt.setString(4, comentario);
			System.out.println(comentario);
			
			psmnt.executeUpdate();
			
		}catch (SQLException e) {
			throw new ExcepcionBD("Error al obtener el último ID de la BD",e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
	}
	
	
	@Override
	public Anotacion obtenerAnotacion(Comic comic, Vineta vineta, Vineta boceto) {

		Anotacion anotacion=null;
		boolean publico=true;
		if(comic == null) publico = false;
		
		
		PreparedStatement psmnt=null;
		try {
			String consulta = "SELECT TEXTO FROM ANOTACION WHERE COMIC_ID";
			int cursor = 1;
		
			if(comic!=null) consulta = consulta +"=?";
			else consulta = consulta +" IS NULL";
			
			consulta = consulta +" AND VINETA_ID";
			if(vineta!=null)consulta = consulta +"=?";
			else consulta = consulta +" IS NULL";
			
			consulta = consulta +" AND BOCETO_ID";
			if(boceto!=null)consulta = consulta +"=?";
			else consulta = consulta +" IS NULL";
			
			System.out.println(consulta);
			
			psmnt = con.prepareStatement(consulta);
			
			if(comic!=null)	psmnt.setInt(cursor++, comic.getID());
			if(vineta!=null) psmnt.setInt(cursor++, vineta.getID());
			if(boceto!=null)psmnt.setInt(cursor++, boceto.getID());
			
			ResultSet salida = psmnt.getResultSet();
			
			if (salida.next() ) {
				anotacion = new Anotacion(comic, vineta, boceto, publico);
				anotacion.setComentario(salida.getString("TEXTO"));
			}

		
		}catch(SQLException e){
			throw new ExcepcionBD("Error al obtener anotación (Comic, vineta, boceto): ("+
		comic.getID()+", "+vineta.getID()+", "+boceto.getID()+")",e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}
		
		return anotacion;
		
	}

	@Override
	public void borrarAnotacion(Comic comic, Vineta vineta, Vineta boceto) {
		PreparedStatement psmnt=null;
		try {
					String consulta = "DELETE FROM ANOTACION WHERE COMIC_ID";
					int cursor = 1;
				
					if(comic!=null) consulta = consulta +"=?";
					else consulta = consulta +" IS NULL";
					
					consulta = consulta +" AND VINETA_ID";
					if(vineta!=null)consulta = consulta +"=?";
					else consulta = consulta +" IS NULL";
					
					consulta = consulta +" AND BOCETO_ID";
					if(boceto!=null)consulta = consulta +"=?";
					else consulta = consulta +" IS NULL";
					
					psmnt = con.prepareStatement(consulta);
					
					if(comic!=null)	psmnt.setInt(cursor++, comic.getID());
					if(vineta!=null) psmnt.setInt(cursor++, vineta.getID());
					if(boceto!=null)psmnt.setInt(cursor++, boceto.getID());
					
					psmnt.executeUpdate();
		}catch(SQLException e){
			throw new ExcepcionBD("Error al borrar anotación (Comic, vineta, boceto): ("+
		comic.getID()+", "+vineta.getID()+", "+boceto.getID()+")",e);
		}finally {
			try {
				psmnt.close();
			} catch (SQLException e) {
				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
			}
		}		
					
	}
	

	@Override
	public List<Alarma> getAlarmas() {

//		List<Alarma> resultado = new ArrayList<>();
//		PreparedStatement psmnt = null;
//		try {
//				psmnt = con.prepareStatement(
//						"SELECT * from ALARMAS");
//				ResultSet rs = psmnt.executeQuery();
//				
//				if(rs.next()) {
//					java.util.Date time=new java.util.Date((long)rs.getInt("FECHA")*1000);
//					PreparedStatement psmnt2 = con.prepareStatement("Select ID from Comic c, vineta v, comic_vineta cv where v.id = cv.vineta_id and c.id = cv.comic_id");
//					ResultSet rs2 = psmnt2.executeQuery();
//					resultado.add(new Alarma(rs2.getInt("ID"), rs.getInt("ID"),));
//				}
//				
//				
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new ExcepcionBD("Error al obtener el último ID de la BD",e);
//		}finally {
//			try {
//				psmnt.close();
//			} catch (SQLException e) {
//				throw new ExcepcionBD("Error al finalizar sentencia("+e.getMessage()+")");
//			}
//		}
//			
//			return resultado;
		
		return new ArrayList<Alarma>();
	}

	@Override
	public int addAlarma(Alarma a) {

		PreparedStatement psmnt = null;

		try {
			psmnt = con.prepareStatement("INSERT INTO ALARMA (ID, FECHA, VINETA_ID) VALUES (?,?,?)");

			psmnt.setInt(1, a.getIdentificador());
			psmnt.setString(2, new String(
					a.getAno() + "-" + a.getMes() + "-" + a.getDia() + " " + a.getHora() + ":" + a.getMinuto()));
			psmnt.setInt(3, a.getVineta().getID());

			psmnt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a.getIdentificador();
	}

	@Override
	public void removeAlarma(Alarma a) {

		PreparedStatement psmnt = null;
		
		try {
			psmnt = con.prepareStatement("DELETE FROM Alarma WHERE ID = ?");
			psmnt.setInt(1, a.getIdentificador());
			psmnt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
public void addMedio(MedioComunicacion m) {
		
PreparedStatement psmnt = null;
		
		try {
			psmnt = con.prepareStatement(
					"INSERT INTO MEDIO_DIFUSION (ID,NOMBRE) VALUES (?,?)");
			PreparedStatement psmnt2 = con.prepareStatement("Select MAX(ID) FROM MEDIO_DIFUSION");
			ResultSet rs = psmnt2.executeQuery();
			psmnt.setInt(1, rs.getInt("ID"));
			psmnt.setString(2, m.getNombre());
			psmnt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<MedioComunicacion> getMedios(){
		List<MedioComunicacion> resultado = new ArrayList<>();
		PreparedStatement psmnt = null;
		
		try {
			psmnt = con.prepareStatement(
					"SELECT * from MEDIO_DIFUSION");
			ResultSet rs = psmnt.executeQuery();
			
			if(rs.next()) {
				resultado.add(new MedioComunicacion(rs.getString("NOMBRE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

}