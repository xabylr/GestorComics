package gestorComics;

import java.awt.Image;

public class Vineta extends Obra{
	
	Image imagen;

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image i) {
		imagen = i;
	}
	
	public Vineta() {
		setID(-1);
	}

	public Vineta(String n) {
		this();
		setNombre(n);
	}
	
	public Vineta(Image i) {
		this();
		setImagen(i);
	}
	
	public Vineta(Image i, String n) {
		this();
		setImagen(i);
		setNombre(n);
	}

	
	@Override
	public int compareTo(Obra o) {
		return getNombre().compareTo(o.getNombre());
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
