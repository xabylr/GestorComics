package gestorComics;

import java.util.Comparator;
import java.util.List;
/*
 * Esta clase abstracta no contiene sentencias para sincronizar con la galería.
 * Esto debe de hacerlo cada implementación concreta.
 */
public abstract class Obra implements Comparable<Obra>, Visualizable, Sincronizable, Anotable{
	
	protected int ID=-1;
	protected String nombre;
	protected AnotacionPublica anotacionPublica;
	
	
	@Override
	public int compareTo(Obra o) {
	OrdenAlfabetico ord = new OrdenAlfabetico();
		return (ord.compare(this, o) );
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String n) {
		nombre = n;
	}
	
	public int getID() {
		return ID;
	}
	
	
	public void setID(int id) {
		ID = id;
	}

	
	@Override
	public void setAnotacionPublica(AnotacionPublica a) {
		anotacionPublica = a;
	}
	
	@Override
	public AnotacionPublica getAnotacionPublica() {
		return anotacionPublica;
	}
	
	@Override
	public void delAnotacionPublica() {
		anotacionPublica=null;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(this.getClass()) && getID() == ((Obra) obj).getID();
	}
	
	@Override
	public int hashCode() {
		return getID();
	}
	
}

class OrdenAlfabetico implements Comparator<Obra>{

	@Override
	public int compare(Obra o1, Obra o2) {
		
		return o1.getNombre().compareTo(o2.getNombre());
	}
	
}

class OrdenID implements Comparator<Obra>{

	@Override
	public int compare(Obra o1, Obra o2) {
		
		return Integer.compare(o1.getID(), o2.getID());
	}
	
}