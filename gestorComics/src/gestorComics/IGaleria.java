package gestorComics;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import excepciones.ExcepcionBD;

/*
 * Galería siguiendo el patrón singleton
 */
public interface IGaleria {
	
	public void insertAll(Collection<Obra> c) throws IOException, SQLException;
	public Obra get(Integer id);
	public List<Obra> getAll();
	public List<Comic> getComics(); 
	
	/*
	 *Devolver siguiente ID disponible 
	 */
	public int nextID();
	Comic getComic(String nombre);
	Vineta getVineta(String nombre);
	List<Vineta> getVinetas(Comic c) throws SQLException;
	void insertarComic(Comic c) throws ExcepcionBD;
	void insertarVineta(Vineta v, Comic c) throws ExcepcionBD;
	public void conectar(IBD bd) throws SQLException, IOException;

}
