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

	
	public void conectar(IBD b) throws SQLException, IOException {
		bd=b;
		getGaleria();
		insertAll(bd.getObras());
	}
	
	
	@Override
	public void insertarComic(Comic c) throws ExcepcionBD {
		if (c.getID()==-1) { //si es local
			ultimoID++;
			c.setID(ultimoID);
			obras.add(c);
			if(bd!=null)
				try {
					bd.insertarComic(c);
					System.out.println("Comic insertado en la base de datos");
				} catch (ExcepcionBD e) {
					e.printStackTrace();
					throw new ExcepcionBD("Error al insertar cómic ("+e.getMessage()+")");
				}
			
		}else { //si venía de la bbdd
			obras.add(c);
			if(c.getID() > ultimoID) ultimoID=c.getID();
		}	
		
	}
	
	@Override
	public void insertarVineta(Vineta v, Comic c) throws ExcepcionBD {
		if (v.getID()==-1) { //si es local se le asigna un id
			ultimoID++;
			v.setID(ultimoID);
			if(c == null)//Si es una viñeta suelta
				obras.add(v);
			else c.addVineta(v);
			
			if(bd!=null)
				try {
					bd.insertarVineta(v,c);
				} catch (ExcepcionBD e) {
					e.printStackTrace();
					throw new ExcepcionBD("Error al insertar viñeta ("+e.getMessage()+")");
				}
			
		}else { //si venía de la bbdd se usa su id y se obtiene el máximo id
			if(c == null) {//Si es una viñeta suelta
				obras.add(v);
			}else c.addVineta(v);
			
			if(v.getID() > ultimoID) ultimoID=v.getID();
		}	
		
	}
	
	

	@Override
	public void insertAll(Collection<Obra> c) throws IOException, SQLException {
		for(Obra o : c) {
			if(o instanceof Vineta) insertarVineta((Vineta)o, null);
			else if(o instanceof Comic) insertarComic((Comic)o);
			
		}
	
	}
	
	/*private void buscarUltimoID() {
		Iterator<Obra> it = obras.iterator();
		while(it.hasNext()) {
			int indice = it.next().getID();
			if (indice > ultimoID) ultimoID = indice;
		}
	}*/
	
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
	
	@Override
	public Comic getComic(String nombre){
		Iterator<Obra> it = obras.iterator();
		boolean encontrado = false;
		Obra obra = null;
		
		while(!encontrado && it.hasNext()) {
			obra = it.next();
			if(obra instanceof Comic && obra.getNombre().equals(nombre)) encontrado=true;
		}
		if(!encontrado) throw new RecursoNoEncontrado("Comic: "+nombre);
		
		return (Comic)obra;
	}
	
	@Override
	public Vineta getVineta(String nombre) {
		Iterator<Obra> it = obras.iterator();
		boolean encontrado = false;
		Obra obra = null;
		
		while(!encontrado && it.hasNext()) {
			obra = it.next();
			if(obra instanceof Vineta && obra.getNombre().equals(nombre)) encontrado=true;
		}
		if(!encontrado) throw new RecursoNoEncontrado("Viñeta: "+nombre);
		
		return (Vineta)obra;
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
	public List<Vineta> getVinetas(Comic c) throws SQLException{
		if(bd==null) return c.getVinetas();
		else return bd.getVinetas(c);

		
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
