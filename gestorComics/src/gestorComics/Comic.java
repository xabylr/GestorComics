package gestorComics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Comic extends Obra{

	private static final String NOMBRE_POR_DEFETO="Viñeta sin nombre";

	private Vineta portada;
	private List<Vineta> vinetas;
	
	public Comic() {
		this(NOMBRE_POR_DEFETO);
	}
	
	public Comic(String n) {
		nombre=n;
		vinetas = new ArrayList<Vineta>();
	}


	@Override
	public int compareTo(Obra o) {
		int resultado;
		if(ID==-1 || o.getID() == -1)
			resultado = nombre.compareTo(o.getNombre());
		else resultado = Integer.compare(ID, o.getID());
		
		return resultado;
	}

	
	public void addVineta(Vineta v){
		vinetas.add(v);
	}
	
	public List<Vineta> getVinetas(){
		return vinetas;
	}

	public void setPortada(Vineta v) {
		portada=v;
	}
	
	public Vineta getPortada() {
		return portada;
	}
	
	@Override
	public String toString() {
		return "Cómic: "+nombre;
	}

	@Override
	/*
	 * Puede devolver null
	 */
	public Image vistaPrevia() {
		if(portada==null) return null;
		return portada.vistaPrevia();
	}


}
