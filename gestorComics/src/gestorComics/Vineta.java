package gestorComics;

import java.awt.Image;
import java.util.List;

public class Vineta extends Obra{
	
	private IBD bd;
	private Image imagen;

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image i) {
		imagen = i;
		if(bd!=null) bd.actualizarImagenVineta(this);
	}
	
	public Vineta() {

	}

	public Vineta(String n) {
		this();
		nombre = n;
	}
	
	public Vineta(Image i) {
		this();
		imagen = i;
	}
	
	public Vineta(Image i, String n) {
		this();
		imagen = i;
		nombre = n;
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

	@Override
	public void conectar(IBD b) {
		bd = b;
		
	}
	
	@Override
	public void subir() {
		System.out.println("NO SE HACE NADA PARA SINCRONIZAR UNA VIÑETA AÚN");
	}

	@Override
	public void retirar() {
		bd = null;
		
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

}
