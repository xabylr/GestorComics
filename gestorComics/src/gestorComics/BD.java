
package gestorComics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.imageio.ImageIO;
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
		try
		{
        // create a connection to the database
			con = DriverManager.getConnection(STR_SQLITE+URL_BD);
			PreparedStatement psmnt = con.prepareStatement("PRAGMA FOREIGN_KEY=ON");
			psmnt.executeUpdate();
		}
		catch (SQLException ex)
		{
			throw new Error("Error al Conectar con la base de datos." + ex.getMessage());
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
		try {
			archivoDDL.setReadable(true);
			Statement stmt = con.createStatement();
			
			Scanner sc = new Scanner(new FileReader(archivoDDL));
			sc.useDelimiter(";");
			
			while(sc.hasNext()){
				String next = sc.next();
				stmt.executeUpdate(next);
			}

			sc.close();
		}catch(Exception e) {
			throw new ExcepcionBD("Error al general la BD", e);
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
		
		//if(v.getID() > ultimoIdVineta) ultimoIdVineta=v.getID();
try {
		if(vineta.getID()==-1) {
			vineta.setID(++ultimoIdVineta);
			
		}
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write((BufferedImage)vineta.getImagen(), "png", baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());

		//creamos la sentencia SQL para subir la imagen	
		PreparedStatement psmnt = con.prepareStatement(
				"INSERT INTO VINETA (NOMBRE, ID, IMAGEN) VALUES (?,?,?)");
			psmnt.setString(1, vineta.getNombre());
			psmnt.setInt(2, vineta.getID());
			psmnt.setBinaryStream(3, is, baos.size() );
				
		psmnt.executeUpdate(); 
	
			psmnt = con.prepareStatement(
					"INSERT INTO COMIC_VINETA (VINETA_ID, COMIC_ID) VALUES(?,?)");
			psmnt.setInt(1, vineta.getID());
			psmnt.setInt(2, comic);
			
			psmnt.executeUpdate();

	
}catch (SQLException | IOException e) {
	throw new ExcepcionBD("Error al insertar  "+vineta, e);
}
	
	}
	
	
	@Override
	public void insertarComic(Comic comic) throws ExcepcionBD  {
try {
	
		if(comic.getID()==-1)comic.setID(++ultimoIdComic);
	

		PreparedStatement psmnt = con.prepareStatement(
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
		}
		
	}

	@Override
	public List<Comic> getComics() throws ExcepcionBD{
		//lista a devolver de cómics
		List<Comic> resultado = new ArrayList<>();
		
		try {
		//obtener cómics con su viñeta de portada
		PreparedStatement psmnt = con.prepareStatement("SELECT ID, NOMBRE, PORTADA FROM COMIC");
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
		}
		
			return resultado;	
	}
	
	
	
	private Vineta getVineta(int id) throws ExcepcionBD  {
		if (id == -1) return null;
		
		try {
			PreparedStatement psmnt = con.prepareStatement("SELECT IMAGEN, NOMBRE, ID"
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
		
		try {
		//SELECT * FROM VINETA, COMIC_VINETA WHERE COMIC_VINETA.COMIC_ID = ?;
		PreparedStatement psmnt = con.prepareStatement(
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
		}		
		
		return resultado;
	}

	@Override
	public int getUltimoIdComic() {
		int resultado=-1;
	try {
			PreparedStatement psmnt = con.prepareStatement(
					"SELECT MAX (ID) FROM COMIC");
			ResultSet rs = psmnt.executeQuery();
			if(rs.next()) resultado = rs.getInt(1);
			
	} catch (SQLException e) {
		throw new ExcepcionBD("Error al obtener el último ID de la BD", e);
	}
		
		return resultado;
	}
	
	@Override
	public int getUltimoIdVineta() {
		int resultado=-1;
	try {
			PreparedStatement psmnt = con.prepareStatement(
					"SELECT MAX (ID) FROM VINETA");
			ResultSet rs = psmnt.executeQuery();
			if(rs.next()) resultado = rs.getInt(1);
			
	} catch (SQLException e) {
		e.printStackTrace();
		throw new ExcepcionBD("Error al obtener el último ID de la BD",e);
	}
		
		return resultado;
	}
	

	@Override
	public void setPortadaComic(int v, int c) {
		try {
			PreparedStatement psmnt = con.prepareStatement(
					"UPDATE COMIC SET PORTADA="+v+" WHERE ID="+c);
			psmnt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al poner portada (ID: "+v+") en comic (ID: "+c+") ("+e.getMessage()+")");
		}
		
	}

	@Override
	public void renombrarVineta(Vineta v, String n) throws ExcepcionBD {
		try {
			PreparedStatement psmnt = con.prepareStatement(
					"UPDATE VINETA SET NOMBRE=? WHERE ID=?");
			psmnt.setString(1, n);
			psmnt.setInt(2, v.ID);
			psmnt.executeQuery();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al renombrar viñeta (ID: "+v+") ("+e.getMessage()+")");
		}
	}

	@Override
	public void renombrarComic(int c, String n) throws ExcepcionBD {

		PreparedStatement psmnt;
		try {
			psmnt = con.prepareStatement(
					"UPDATE COMIC SET NOMBRE = "+n+" WHERE ID = "+c);
			psmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al renombrar el comic");
		}
		
	}

	@Override
	public void borrarVineta(int v, int c) throws ExcepcionBD {

		PreparedStatement psmnt;
		try {
			psmnt = con.prepareStatement(
					"DELETE FROM VINETA WHERE ID = "+v);
			psmnt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al borrar viñeta");
		}
		
	}

	@Override
	public void borrarComic(int c) throws ExcepcionBD {
		
		PreparedStatement psmnt;
		try {
			psmnt = con.prepareStatement(
					"DELETE FROM COMIC WHERE ID = "+c);
			psmnt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD("Error al borrar comic");
		}
		
	}

	@Override
	public Vineta getPortada(int c) throws ExcepcionBD {
		// TODO Auto-generated method stub
		System.out.println("CONSEGUIR PORTADA EN BD NO IMPLEMENTADO");
		return null;
	}

	@Override
	public void insertarVinetas(List<Vineta> vs, int c) {
		// TODO Auto-generated method stub
		System.out.println("INSERTAR VIÑETAS EN BD NO IMPLEMENTADO");
	}

	@Override
	public void actualizarImagenVineta(Vineta v) {
		System.out.println("ACTUALIZAR IMAGEN VIÑETA EN BD NO IMPLEMENTADO");
		
	}

	@Override
	public List<Vineta> getBocetos(int iD) {
		System.out.println("OBTENER LISTA DE BOCETOS EN BD NO IMPLEMENTADO");
		return new ArrayList<>();
	}

	@Override
	public void insertarAnotacion(Comic comic, Vineta vineta, Vineta boceto, String comentario) {
		System.out.println("INSERTAR ANOTACION EN BD NO IMPLEMENTADO");
	}
	
	@Override
	public Anotacion obtenerAnotacion(Comic comic, Vineta vineta, Vineta boceto) {
		System.out.println("OBTENER ANOTACION EN BD NO IMPLEMENTADO");
		return null;
		
	}

	@Override
	public void borrarAnotacion(Comic comic, Vineta vineta, Vineta boceto) {
		System.out.println("BORRAR ANOTACION EN BD NO IMPLEMENTADO");
		
	}
	
}