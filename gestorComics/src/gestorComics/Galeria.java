package gestorComics;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import excepciones.ExcepcionBD;
import excepciones.RecursoNoEncontrado;


/*
 * Colección de Obras del modelo siguiendo el esquema singleton
 */
public class Galeria implements IGaleria {
	private List<Comic> comics;
	
	private IBD bd;
	
	public Galeria(){
		comics = new ArrayList<>(); //A discutir el tipo de lista
	}
	
	public Galeria(IBD b) {
		this();
		conectar(b);
	}

	
	public void conectar(IBD b) {
		bd=b;
		try {
			cargarComics(bd.getComics());
		} catch (ExcepcionBD e) {
			throw new ExcepcionBD(
					"Error al conectar la galería a la base de datos", e);
		}
	}
	
	//Guarda en BBDD y local
	@Override
	public void guardarComic(Comic c) throws ExcepcionBD {
		if(bd!=null) {
			c.conectar(bd);
			c.subir();
		}
		
		comics.add(c);
	}
	
	
	//Carga de BBDD a local
	@Override
	public void cargarComic(Comic c) throws ExcepcionBD {
		//c.conectar(bd); //ya se hace en BD
		comics.add(c);
	}
	

	
	@Override
	public void cargarComics(Collection<Comic> comics){	
		for(Comic c : comics) {
			cargarComic(c);	
		}
	}
	
	
	
	@Override
	public Comic getComic(int id){
		Iterator<Comic> it = comics.iterator();
		boolean encontrado = false;
		Comic comic = null;
		
		while(!encontrado && it.hasNext()) {
			comic = it.next();
			if(comic.getID() == id) encontrado=true;
		}
		//Todos los cómics de la base de datos se cargan en memoria, así que no buscamos allí.
		if(!encontrado) throw new RecursoNoEncontrado("Cómic (ID "+id+")");
		
		return comic;
	}
	
	@Override
	public Vineta getVineta(Comic c, int id){
		Iterator<Vineta> it = c.getVinetas().iterator();
		boolean encontrado = false;
		Vineta vineta = null;
		
		while(!encontrado && it.hasNext()) {
			vineta = it.next();
			if(vineta.getID() == id) encontrado=true;
		}
		//Todos los cómics de la base de datos se cargan en memoria, así que no buscamos allí.
		if(!encontrado) throw new RecursoNoEncontrado("Viñeta (ID "+id+") en "+c);
		
		return vineta;
	}
	
	@Override
	public Comic getComic(String nombre){
		Iterator<Comic> it = comics.iterator();
		boolean encontrado = false;
		Comic comic = null;
		
		while(!encontrado && it.hasNext()) {
			comic = it.next();
			if(comic instanceof Comic && comic.getNombre().equals(nombre)) encontrado=true;
		}
		if(!encontrado) throw new RecursoNoEncontrado("Cómic con nombre: \""+nombre+"\"");
		
		return comic;
	}


	@Override
	public List<Comic> getComics() {
		return comics;
	}


	@Override
	public void borrarComic(Comic c) throws RecursoNoEncontrado {
		c.retirar();
		comics.remove(c);
		
	}
	
	
}
