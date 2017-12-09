package gestorComics;

public class AnotacionPublica extends Anotacion {

	public AnotacionPublica(String c) {
		super(c);
	}

	@Override
	public boolean esPublico() {
		return true;
	}

}
