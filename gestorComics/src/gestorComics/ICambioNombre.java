package gestorComics;

import java.awt.event.ActionListener;

public interface ICambioNombre {

	public static final String ACEPTAR = "ACEPTAR";
	public static final String CANCELAR = "CANCELAR";
	
	public String getNombre();
	
	public void controlador(ActionListener ctr);
	
	public void dispose();
	
}
