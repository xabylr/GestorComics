package gestorComics;

import java.awt.Image;

public class Vineta implements Obra{
	
	String nombre;
	Image imagen;
	int ID;
	
	public Image getImagen() {
		return imagen;
	}


	public void setImagen(Image i) {
		imagen = i;
	}
	
	public Vineta() {
		ID=-1;
	}

	public Vineta(String n) {
		nombre=n;
	}
	
	public Vineta(Image i) {
		imagen=i;
	}
	
	public Vineta(Image i, String n) {
		nombre=n;
		imagen=i;
	}
	
	public Vineta(Image i, String n, int id) {
		this(i, n);
		ID = id;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String n) {
		nombre=n;
		
	}

	@Override
	public int compareTo(Obra o) {
		return getNombre().compareTo(o.getNombre());
	}

	@Override
	public int getID() {
		return ID;
	}


	@Override
	public Image vistaPrevia() {
		return imagen;
	}

	@Override
	public String toString() {
		return "Viñeta: "+nombre+" (ID "+ID+")";
	}

}
