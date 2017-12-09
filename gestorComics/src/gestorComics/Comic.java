package gestorComics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import excepciones.ExcepcionBD;
import excepciones.RecursoNoEncontrado;

public class Comic extends Obra{

	private static final String NOMBRE_POR_DEFETO="Viñeta sin nombre";

	protected IBD bd;
	
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
	public void guardar() {
		bd.insertarComic(this); //Se le asigna un ID
	}
	

	@Override
	public void desConectar() {
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


	public void inicializarVinetas() { //cargar lista de viñetas
		if (vinetas == null) {
			if (bd != null) {
				vinetas = bd.getVinetas(ID);
				
				Vineta aux = bd.getPortada(ID);
				
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
		if(portada == null) setPortada(v);
			if(bd!=null) {
					bd.insertarVineta(v, ID);
			}
		
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
		return portada.vistaPrevia();
	}


}
