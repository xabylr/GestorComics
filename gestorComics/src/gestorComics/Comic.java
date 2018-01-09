package gestorComics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import excepciones.RecursoNoEncontrado;
import gui.Observador;

public class Comic extends Obra implements Observable, Observador{

	private static final String NOMBRE_POR_DEFETO="Viñeta sin nombre";

	protected IBD bd;
	
	private Collection<Observador> observadores;
	
	private Vineta portada;
	private List<Vineta> vinetas; //nulo si no se han obtenido
	
	private List<Vineta> bocetos; //nulo si no se han obtenido
	
	public Comic() {
		this(NOMBRE_POR_DEFETO);
	}
	
	public Comic(String n) {
		nombre=n;
		observadores = new ArrayList<>();
	}
	
	@Override
	public void conectar(IBD b) {
		bd = b;
	}
	
	@Override
	public void subir() {
		bd.insertarComic(this); //Se le asigna un ID
	}
	

	@Override
	public void retirar() {
		for(Vineta v : vinetas) {
			v.retirarDe(this);
		}
		
		bd.borrarComic(ID);
		bd=null;
		ID=-1;
		notificarTodosBorrado();
		
	}

	public void retirarVineta(Vineta v) {
		v.retirarDe(this);
		vinetas.remove(v);
		notificarBorrado(v);
	}

	@Override
	public void setNombre(String n) {
		super.setNombre(n);
		if(bd!=null) bd.renombrarComic(ID, n);
		notificarTodos();
		
	}
	

	@Override
	public int compareTo(Obra o) {
		int resultado;
		if(ID==-1 || o.getID() == -1)
			resultado = nombre.compareTo(o.getNombre());
		else resultado = Integer.compare(ID, o.getID());
		
		return resultado;
	}

	//Si ya había viñetas antes de conectar, se pierden
	public void inicializarVinetas() { //cargar lista de viñetas
		if (vinetas == null) {
			if (bd != null) {
				vinetas = bd.getVinetas(ID);
				
				Vineta aux = bd.getPortada(ID);
				
		//(PRESCINDIBLE) usar la misma viñeta de portada de entre las que ya tenemos
		//Así se evita usar la copia necesaria antes de tener la lista de viñetas
				if (aux != null)
					for (Vineta v : vinetas)
						if(v.equals(aux)) portada = v;
			
			//registrar en busca de cambios
			for(Vineta v : vinetas) {
				v.registrar(this);
			}
			
			
			}
			else vinetas = new ArrayList<Vineta>();
		}
	}
	
	
	public void inicializarBocetos(){
		
		if (bocetos == null && bd != null) {
		bocetos = bd.getBocetos(ID);
		}else bocetos = new ArrayList<Vineta>();
		
	}
	
	public void addVineta(Vineta v){
		inicializarVinetas();
		v.registrar(this);
		vinetas.add(v);
		
		//primero guardamos la viñeta para que tenga un ID en la BD
		if(bd!=null) {
			bd.insertarVineta(v, ID); //aquí se conecta
		}
		
		//Después se puede poner de portada incluso en la BD
		if(portada == null) setPortada(v);
		
	}
	
	public void addVinetas(List<Vineta> vs) {
		inicializarVinetas();
		if(portada == null) setPortada(vs.get(0));
		vinetas.addAll(vs);
		if (bd != null) bd.insertarVinetas(vs, ID);
	}
	
	public List<Vineta> getVinetas(){
		inicializarVinetas();
		return vinetas;
	}

	public Vineta getVineta(int id){
		inicializarVinetas();
		Iterator<Vineta> it = vinetas.iterator();
		boolean encontrado = false;
		Vineta vineta = null;
		
		while(!encontrado && it.hasNext()) {
			vineta = it.next();
			if(vineta.getID() == id) encontrado=true;
		}

		if(!encontrado) throw new RecursoNoEncontrado("Viñeta (ID "+id+") en "+this);
		
		return vineta;
	}
	
	public void setPortada(Vineta v) {
		if (bd != null) bd.setPortadaComic(v, this);
		portada=v;
		notificarTodos();
	}
	
	public Vineta getPortada() {
		if (portada == null && bd != null )portada = bd.getPortada(ID); 
		return portada;
	}
	
	@Override
	public String toString() {
		return "Cómic: "+nombre+ "(ID "+ID+")";
	}
	
	
	/*
	 * Puede devolver null
	 */
	@Override
	public Image vistaPrevia() {
		if(portada==null) return null;
		return getPortada().vistaPrevia();
	}



	@Override
	public void registrar(Observador o) {
		observadores.add(o);
	}
	
	@Override
	public void darbBaja(Observador o) {
		observadores.remove(o);
		
	}
	
	@Override
	public boolean observadoPor(Observador o){
		return observadores.contains(o);
	}
	
	public List<Vineta> getBocetos() {
		
		inicializarBocetos();
		
		/*
		bocetos.add(new Vineta());
		bocetos.add(new Vineta());
		bocetos.add(new Vineta());
		bocetos.add(new Vineta());
		bocetos.add(new Vineta());
		bocetos.add(new Vineta());
		bocetos.add(new Vineta());
		*/
		
		return bocetos;
	}
	

	@Override
	public void notificarTodos() {
		for(Observador o : observadores){
			o.notificar();
		}
	}
	
	@Override
	public void notificarTodosBorrado() {
		for(Observador o : observadores){
			o.notificarBorrado(this);
		}
	}

	@Override
	public void notificar() {
		notificarTodos();
	}


	@Override
	public void notificarBorrado(Observable o) {
		//TODO comprobar portada
		vinetas.remove((Vineta)o);
		boolean encontrado=false;
		if(portada !=null) {
			for (Vineta v : vinetas)
				if (v.getID()==portada.getID())
					encontrado=true;
		}else encontrado = true;
			
		if(!encontrado) {
			if (vinetas.isEmpty()) setPortada(null);
			else setPortada(vinetas.get(1));
		}
		
		notificarTodos();	
	}

}
