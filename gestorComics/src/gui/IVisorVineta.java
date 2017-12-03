package gui;

import java.awt.Image;
import java.awt.event.ActionListener;

public interface IVisorVineta {
	
	public static String CANCELAR="CANCELAR";

	public void controlador(ActionListener ctr); 
	
	public void SetImagen(Image img);
	
	public void SetNombre(String name);
}
