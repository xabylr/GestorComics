package gestorComics;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Anotacion implements Historico {

	protected String comentario;
	/*
	 * IMPORTANTE: usar java.sql.Time para BD
	 */
	protected LocalDateTime fecha;
	
	
	public Anotacion(String c) {
		comentario = c;
		fecha = LocalDateTime.now();
	}
	
	
	@Override
	public void setFecha(LocalDateTime f) {
		fecha = f;
	}
	@Override
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	
	public abstract boolean esPublico();
	
	public String getComentario(){
		return comentario;
	}
	
	public void setComentario(String c) {
		fecha = LocalDateTime.now();
		comentario = c;
	}
	
	
}
