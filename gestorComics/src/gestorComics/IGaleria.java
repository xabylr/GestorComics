package gestorComics;


import java.util.Collection;
import java.util.List;

import excepciones.ExcepcionBD;
import excepciones.RecursoNoEncontrado;
import gui.IVentanaGaleria;

/*
 * Galería que puede conectarse a una GUI para actualizar su contenido
 */
public interface IGaleria {
	/*
	 * Utilizado principalmente por la propia clase y para pruebas sin BD
	 */
	public void cargarComics(Collection<Comic> c);
	
	//Búsqueda
	public List<Comic> getComics(); 
	public Comic getComic(int id);
	public Comic getComic(String nombre);
	public Vineta getVineta(Comic c, int id);
	
	//Escritura
	
	/*
	 * Sube el cómic con sus viñetas si las tiene y le asigna un ID
	 */
	public void guardarComic(Comic c) throws RecursoNoEncontrado;

	/*
	 * Carga un cómic en la galería local
	 */
	public void cargarComic(Comic c) throws RecursoNoEncontrado;
	
	
	/*
	 * Borra el cómic y sus viñetas
	 */
	public void borrarComic(Comic c) throws RecursoNoEncontrado;
	/*
	 * Si la viñeta no se usa en más cómics la borra completamente
	*/
	
	
	public void anadirMedio(MedioComunicacion m);
	
	public List<MedioComunicacion> getMedios();
	
	
	//Base de datos
	public void conectarBD(IBD bd) throws ExcepcionBD;
	
	//GUI
	public void conectarGUI(IVentanaGaleria g);

	public void refrescarLista();

	public ManagerAlarmas getManager();

}
