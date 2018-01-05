package gestorComics;

import controladores.IObserver;

public interface IObservable {
	public void notificar();
	public void registrar(IObserver o);
	public void darBaja(IObserver o);
}
