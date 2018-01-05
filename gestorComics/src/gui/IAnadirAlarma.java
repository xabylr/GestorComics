package gui;

import gestorComics.Comic;
import gestorComics.Vineta;

public interface IAnadirAlarma {

	public static final String FECHA = "FECHA";
	public static final String ANADIR ="AÑADIR";
	public void addComic(Comic c);
	public Comic getComic();
	public void addVineta(Vineta v);
	public Vineta getVineta();
	
}
