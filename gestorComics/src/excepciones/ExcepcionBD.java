package excepciones;

@SuppressWarnings("serial")
public class ExcepcionBD extends RuntimeException {

	String mensaje;
	
	public ExcepcionBD() {
		this("Error de Base de Datos no especificado");
	}

	public ExcepcionBD(String reason) {
		mensaje=reason;
		System.err.println("Excepción de BD: "+reason);
	}
	
	@Override
	public String getMessage() {
		return mensaje;
	}

}
