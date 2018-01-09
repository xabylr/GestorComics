package gestorComics;

public interface Anotable {

	public Anotacion getAnotacionPublica(Comic c);
	public Anotacion getAnotacionPrivada();
	
	void delAnotacionPrivada();
	void delAnotacionPublica(Comic c);
	void setAnotacion(Anotacion a);

	
	
}
