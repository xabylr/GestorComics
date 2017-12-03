
package gestorComics;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.imageio.ImageIO;

public class BD implements IBD{
	private static final String RES_FOLDER = "res";
	private static final String FILE_BD = "dibujante.db";
	private static final String FILE_DDL = "generadorBD.ddl";
	private static final String CARPETA_PROYECTO = "GestorComics";
	private static final String STR_SQLITE = "jdbc:sqlite:";
	private static String URL_BD = getUrlBDGenerator() + "/"+ FILE_BD;
	private static File archivoDDL;
	static{URL_BD= URL_BD.replace("\\", "/");
	archivoDDL = new File(RES_FOLDER+"/"+FILE_DDL);
	}

	private Connection con ;
	private File archivoBD;
	
	
	private static BD bd;
	
	public static BD getBD(){
		
		return bd==null? bd = new BD() : bd;
		
	}
	
	private BD(){
		new File(BD.getUrlBDGenerator()).mkdirs();
		archivoBD = new File(URL_BD);
		boolean existia = archivoBD.exists();
		Conectar();
		
		if(!existia){
			System.out.println("Base de datos no encontrada: creando una nueva.");
			GenerarBaseDatos();
			
		}
		
        System.out.println("Se ha establecido la conexión con el archivo BBDD");
			
	}
	
	private void Conectar() {
		try
		{
        // create a connection to the database
			con = DriverManager.getConnection(STR_SQLITE+URL_BD);
        
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
	
	private Object SelectEscalar(String sel)
	{
		ResultSet rset;
		Object res = null;
		try
		{
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(sel);
			rset.next();
			res = rset.getObject(1);
			rset.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new Error("Error en el SELECT: " + sel + ". " + ex.getMessage());
		}		
		
		return res;
	}
	
	private List<Object[]> Select(String sel)
	{
		ResultSet rset;
		List<Object[]> lista = new ArrayList<Object[]>();
		try
		{
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(sel);
			ResultSetMetaData meta = rset.getMetaData();
			int numCol = meta.getColumnCount();
			while (rset.next())
			{
				Object[] tupla = new Object[numCol];
				for(int i=0; i<numCol;++i)
				{
					tupla[i] = rset.getObject(i+1);
				}
				lista.add(tupla);
			}
			rset.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new Error("Error en el SELECT: " + sel+ ". " + ex.getMessage());
		}		
		
		return lista;
	}
	
	private void Insert(String ins)
	{
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(ins);
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new Error("Error en el INSERT: " + ins+ ". " + ex.getMessage());
		}
	}

	private void Delete(String del)
	{
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(del);
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new Error("Error en el DELETE: " + del+ ". " + ex.getMessage());
		}
	}

	private void Update(String up)
	{
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(up);
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new Error("Error en el UPDATE: " + up+ ". " + ex.getMessage());
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
			System.out.println("Error: " + e.getMessage());
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
	public void insertarObra(Obra obra) throws IOException, SQLException {

		if(obra instanceof Vineta) {
			
			Vineta vineta =(Vineta) obra; //para no hacer un lío de paréntesis aquí dentro
			
		//Convertimos la imagen a un archivo png en memoria ( stream de array de bytes y a un inputstream ) para subir	
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write((BufferedImage)vineta.getImagen(), "png", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());

			//creamos la sentencia SQL para subir la imagen	
			PreparedStatement psmnt = con.prepareStatement("INSERT INTO VINETA (NOMBRE, ID, IMAGEN) VALUES (?,?,?)");
					psmnt.setString(1, vineta.getNombre());
					psmnt.setInt(2, vineta.getID());
					psmnt.setBinaryStream(3, is, baos.size() );
					
			psmnt.executeUpdate(); 
			System.out.println("Viñeta "+ vineta.getNombre()+ " (ID "+vineta.getID()+ ") insertada en la BBDD");
		
		} else if(obra instanceof Comic){
			Comic comic = (Comic) obra;
			
		}
		
	}

	@Override
	public List<Obra> getObras() throws SQLException {
		// TODO IMPLEMENTAR
		return new ArrayList<Obra>();
	}

	@Override
	public List<Vineta> getVinetas(Comic comic) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}