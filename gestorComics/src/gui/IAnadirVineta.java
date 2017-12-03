package gui;

import gestorComics.Comic;

public interface IAnadirVineta {

	public void alert(String error);
	public void addComic(Comic c);
	public Comic getComic();
}
