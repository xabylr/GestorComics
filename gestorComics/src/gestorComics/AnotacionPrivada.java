package gestorComics;

public class AnotacionPrivada extends Anotacion {

	public AnotacionPrivada(String c) {
		super(c, false);
	}

	@Override
	public boolean esPublico() {
		return false;
	}

}
