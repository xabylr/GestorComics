package gestorComics;

public class AnotacionPublica extends Anotacion {

	public AnotacionPublica(String c) {
		super(c, true);
	}

	@Override
	public boolean esPublico() {
		return true;
	}

}
