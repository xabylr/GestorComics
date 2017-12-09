package gestorComics;

public class AnotacionPrivada extends Anotacion {

	public AnotacionPrivada(String c) {
		super(c);
	}

	@Override
	public boolean esPublico() {
		return false;
	}

}
