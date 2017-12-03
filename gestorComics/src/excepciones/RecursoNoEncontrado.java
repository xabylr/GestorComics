package excepciones;

@SuppressWarnings("serial")
public class RecursoNoEncontrado extends RuntimeException {

	public RecursoNoEncontrado() {
		this("No se ha encontrado algún recurso");
	}

	public RecursoNoEncontrado(String r) {
		System.err.println("No se ha encontrado el recurso: "+r);
	}

}
