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
	
	public ExcepcionBD(String motivo, Exception e) {
		mensaje = motivo+"\n\n~~~~~~~~~~~~~~~~~ (v) Información detallada del error  (v) ~~~~~~~~~~~~~~~~~\n\n"
				+e.getMessage()+
				"\n\n~~~~~~~~~~~~~~~~~ (^) Información detallada del error  (^) ~~~~~~~~~~~~~~~~~\n";
	}
	
	@Override
	public String getMessage() {
		return mensaje;
	}

}
