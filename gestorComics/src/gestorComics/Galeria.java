package gestorComics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import excepciones.RecursoNoEncontrado;


/*
 * Colección de Obras del modelo siguiendo el esquema singleton
 */
public class Galeria implements IGaleria {
	private List<Obra> obras;
	private static Galeria galeria;
	private int ultimoID;
	
	private IBD bd;
	
	private Galeria(){
		obras = new ArrayList<>(); //A discutir el tipo de lista
	}
	
	private Galeria(Collection<Obra> c) {
		obras.addAll(c);
	}

	public static Galeria getGaleria(){
		if (galeria == null) return galeria = new Galeria();
		else return galeria;
	}

	
	public void conectar(IBD b) throws SQLException {
		bd=b;
		getGaleria();
		obras.addAll(bd.getObras());
	}
	
	@Override
	public void insert(Obra o) {
		obras.add(o);
		if(o.getID() > ultimoID) ultimoID=o.getID();
	}

	@Override
	public void insertAll(Collection<Obra> c) {
		obras.addAll(c);
		buscarUltimoID();
	}
	
	private void buscarUltimoID() {
		Iterator<Obra> it = obras.iterator();
		while(it.hasNext()) {
			int indice = it.next().getID();
			if (indice > ultimoID) ultimoID = indice;
		}
	}
	
	@Override
	public Obra get(Integer id){
		Iterator<Obra> it = obras.iterator();
		boolean encontrado = false;
		Obra obra = null;
		
		while(!encontrado && it.hasNext()) {
			obra = it.next();
			if(obra.getID() == id) encontrado=true;
		}
		if(!encontrado) throw new RecursoNoEncontrado("ID "+id);
		
		return obra;
	}
	
	public Obra getComic(String nombre){
		Iterator<Obra> it = obras.iterator();
		boolean encontrado = false;
		Obra obra = null;
		
		while(!encontrado && it.hasNext()) {
			obra = it.next();
			if(obra instanceof Comic && obra.getNombre().equals(nombre)) encontrado=true;
		}
		if(!encontrado) throw new RecursoNoEncontrado("Comic: "+nombre);
		
		return obra;
	}
	public Obra getVineta(String nombre) {
		Iterator<Obra> it = obras.iterator();
		boolean encontrado = false;
		Obra obra = null;
		
		while(!encontrado && it.hasNext()) {
			obra = it.next();
			if(obra instanceof Vineta && obra.getNombre().equals(nombre)) encontrado=true;
		}
		if(!encontrado) throw new RecursoNoEncontrado("Viñeta: "+nombre);
		
		return obra;
	}

	@Override
	public List<Obra> getAll() {
		return obras;
	}

	@Override
	public int nextID() {
		return ultimoID+1;
	}

	@Override
	public List<Comic> getComics() {
		List<Comic> listAux = new ArrayList<>();
		
		for(Obra o : obras) {
			if(o instanceof Comic)listAux.add((Comic) o);
		}
			
		return listAux;
	}
	
	
}
