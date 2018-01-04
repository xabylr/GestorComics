package gestorComics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import controladores.IObserver;
import excepciones.ExcepcionBD;
import excepciones.RecursoNoEncontrado;

public class Comic extends Obra implements IObservable{

	private static final String NOMBRE_POR_DEFETO="Viñeta sin nombre";

	protected IBD bd;
	
	private Collection<IObserver> observadores;
	
	private Vineta portada;
	private List<Vineta> vinetas; //nulo si no se han obtenido
	
	public Comic() {
		this(NOMBRE_POR_DEFETO);
	}
	
	public Comic(String n) {
		nombre=n;
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
		bd.borrarComic(ID);
		bd=null;
		ID=-1;
		
	}
	
	@Override
	public void setNombre(String n) {
		super.setNombre(n);
		if(bd!=null) bd.renombrarComic(ID, n);
		
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
			}
			else vinetas = new ArrayList<Vineta>();
		}
	}
	
	public void addVineta(Vineta v){
		inicializarVinetas();
		vinetas.add(v);
		
		//primero guardamos la viñeta para que tenga un ID en la BD
		if(bd!=null) {
			bd.insertarVineta(v, ID);
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
		if (bd != null) bd.setPortadaComic(v.getID(), ID);
		portada=v;
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
	public void addAnotacionPrivada(AnotacionPrivada a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AnotacionPrivada> getAnotacionesPrivadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delAnotacionPrivada(AnotacionPrivada a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificar() {
		for(IObserver o : observadores){
			o.actualizar();
		}
	}

	@Override
	public void registrar(IObserver o) {
		observadores.add(o);
	}

	@Override
	public void darBaja(IObserver o) {
		observadores.remove(o);
	}


}
