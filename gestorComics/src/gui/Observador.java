package gui;

import gestorComics.Observable;

public interface Observador {
public void notificar();
public void notificarBorrado(Observable o);
}
