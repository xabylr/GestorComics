package gestorComics;

import java.util.Comparator;

public abstract class Obra implements Comparable<Obra>, Visualizable{
	
	protected int ID=-1;
	protected String nombre;
	
	@Override
	public int compareTo(Obra o) {
	OrdenAlfabetico ord =	new OrdenAlfabetico();
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
		ID=id;
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