package gui;

import java.awt.Image;
import java.awt.event.ActionListener;

import gestorComics.Comic;

public interface IVisorComic {
	public static final String ANADIRVINETA = "ANADIRVINETA";
	public static final String ANADIRCOMIC = "ANADIRCOMIC";
	public static final String CAMBIARNOMBRE = "CAMBIARNOMBRE";
	public static final String BORRARCOMIC = "BORRARCOMIC";
	void controlador(ActionListener ctr);
	void SetNombre(String name);
	public Comic getComic();
	public void SetImagen(Image img);
	public void dispose();

}
