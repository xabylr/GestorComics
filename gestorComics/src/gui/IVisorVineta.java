package gui;

import java.awt.Image;
import java.awt.event.ActionListener;

import gestorComics.Vineta;

public interface IVisorVineta {
	
	public static String CANCELAR="CANCELAR";
	public static String BORRAR="BORRAR";
	public static String CAMBIARNOMBRE="CAMBIARNOMBRE";
	public static String CAMBIARIMAGEN="CAMBIARIMAGEN";

	public void controlador(ActionListener ctr); 
	
	public void SetImagen(Image img);
	
	public void SetNombre(String name);
	
	public void dispose();
	
	public Vineta getVineta();
}