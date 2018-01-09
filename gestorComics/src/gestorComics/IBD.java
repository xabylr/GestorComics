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
	public int getEnlaces(int vineta);
	
	
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
	
	void setPortadaComic(Vineta v, Comic c) throws ExcepcionBD;
	void insertarBoceto(Vineta boceto, Comic comic);
	List<Vineta> getBocetos(int iD);
	/*
	 * Si el comic es null es una anotación privada
	 */
	void insertarAnotacion(Comic comic, Vineta vineta, Vineta boceto, String comentario);
	
	/*
	 * Si el comic es null es una anotación privada
	 */
	Anotacion obtenerAnotacion(Comic comic, Vineta vineta, Vineta boceto);
	
	
	void borrarAnotacion(Comic comic, Vineta vineta, Vineta boceto);
	
	public List<Alarma> getAlarmas();
	public int addAlarma(Alarma a);
	public void removeAlarma(Alarma a);
	List<MedioComunicacion> getMedios();
	void addMedio(MedioComunicacion m);
	
}
