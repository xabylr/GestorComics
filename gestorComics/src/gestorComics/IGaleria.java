package gestorComics;


import java.util.Collection;
import java.util.List;

/*
 * Galería siguiendo el patrón singleton
 */
public interface IGaleria {
	
	public void insert(Obra o);
	public void insertAll(Collection<Obra> c);
	public Obra get(Integer id);
	public List<Obra> getAll();
	public List<Comic> getComics(); 
	
	/*
	 *Devolver siguiente ID disponible 
	 */
	public int nextID();

}
