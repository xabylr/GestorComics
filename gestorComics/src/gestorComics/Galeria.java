package gestorComics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import excepciones.ExcepcionBD;
import excepciones.RecursoNoEncontrado;
import gui.IVentanaGaleria;
import gui.Observador;


/*
 * Colección de Obras del modelo con información de una BD y actualizando una GUI
 */
public class Galeria implements IGaleria, Observador //, Observable (No es observable por motivos de retrocompatibilidad)
{
	private List<Comic> comics;
	private List<Observador> observadores;
	private IVentanaGaleria gui;
	
	private IBD bd;
	
	public Galeria(){
		comics = new ArrayList<>();
		observadores = new ArrayList<>();
	}
	
	public Galeria(IBD b) {
		this();
		conectarBD(b);
	}

	
	public void conectarBD(IBD b) {
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
		refrescarGUI();
	}
	
	
	//Carga de BBDD a local
	@Override
	public void cargarComic(Comic c) throws ExcepcionBD {
		cargarComic(c);
		refrescarGUI();
	}
	
	private void cargarComicNoRefresco(Comic c) {
		comics.add(c);
		c.registrar(this);
	}
	

	
	@Override
	public void cargarComics(Collection<Comic> comics){	
		for(Comic c : comics) {
			cargarComicNoRefresco(c);	
		}
		refrescarGUI();
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
		comics.remove(c);
		refrescarLista();
	}

	@Override
	public void conectarGUI(IVentanaGaleria g) {
		gui = g;
		
	}

	@Override
	public void refrescarLista() {
		refrescarGUI();
	}
	
	
	private void refrescarGUI() {
		if(gui!=null) gui.refrescar();
	}

	
//No es observable porque se fusiona con la GUI 
	/*
	@Override
	public void registrar(Observador o) {
		observadores.add(o);
	}

	@Override
	public void darbBaja(Observador o) {
		observadores.remove(o);
	}

	@Override
	public boolean observadoPor(Observador o) {
		return observadores.contains(o);
	}

	@Override
	public void notificarTodos() {
		for (Observador o : observadores) o.notificar();
	}
*/
	@Override
	public void notificar() {
		refrescarLista();
	}

	@Override
	public void notificarBorrado(Observable o) {
		borrarComic((Comic) o);
	}
	
}
