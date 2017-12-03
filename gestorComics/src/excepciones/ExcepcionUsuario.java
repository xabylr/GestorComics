package excepciones;

@SuppressWarnings("serial")
public class ExcepcionUsuario extends RuntimeException {

	String mensaje;
	
	public ExcepcionUsuario() {
		this("Error de usuario no especificado");
	}

	public ExcepcionUsuario(String reason) {
		mensaje=reason;
		System.err.println("Excepción de usuario: "+reason);
	}
	
	@Override
	public String getMessage() {
		return mensaje;
	}

}
