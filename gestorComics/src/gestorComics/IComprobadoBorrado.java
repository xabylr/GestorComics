package gestorComics;

import java.awt.event.ActionListener;

public interface IComprobadoBorrado {
	
	public static final String ACEPTAR = "ACEPTAR";
	public static final String CANCELAR = "CANCELAR";
	
	public void controlador(ActionListener ctr);
	
	public void dispose();
	
	public void setTexto(String s);
}
