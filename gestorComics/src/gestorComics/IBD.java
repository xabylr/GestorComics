package gestorComics;

import java.util.List;

import excepciones.ExcepcionBD;

public interface IBD {

	
	//Búsqueda
	int getUltimoIdComic();
	int getUltimoIdVineta();
	
	public List<Comic> getComics() throws ExcepcionBD;
	public List<Vineta> getVinetas(int c) throws ExcepcionBD;
	public Vineta getPortada(int c) throws ExcepcionBD;
	
	//Modificación
	/*
	 * Inserta la viñeta especificada en la base de datos asociada a un cómic. 
	 * Si comic es null, se inserta suelta
	 */
	public void insertarVineta(Vineta v, int c) throws ExcepcionBD;
	public void insertarVinetas(List<Vineta> vs, int c);
	public void insertarComic(Comic comic) throws ExcepcionBD;
	
	public void renombrarVineta(Vineta v, String n)  throws ExcepcionBD;
	public void renombrarComic(int c, String n) throws ExcepcionBD;
	
	public void actualizarImagenVineta(Vineta v);
	
	/*
	 * Borra la viñeta de un cómic y de la BD si se queda sin enlaces
	 */
	public void borrarVineta(int v, int c) throws ExcepcionBD;
	public void borrarComic(int c) throws ExcepcionBD;
	
	void setPortadaComic(int v, int c) throws ExcepcionBD;

}
