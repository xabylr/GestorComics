package gestorComics;

import java.util.List;

import excepciones.ExcepcionBD;

public interface IBD {

	public List<Obra> getObras() throws ExcepcionBD;
	public List<Vineta> getVinetas(Comic comic)throws ExcepcionBD;
	/*
	 * Inserta la viñeta especificada en la base de datos asociada a un cómic. 
	 * Si comic es null, se inserta suelta
	 */
	void insertarVineta(Vineta vineta, Comic comic) throws ExcepcionBD;
	void insertarComic(Comic comic) throws ExcepcionBD;
	int getUltimoID();

}
