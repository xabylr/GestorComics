package gestorComics;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Anotacion implements Historico {

	protected String comentario;
	
	private boolean publico;
	/*
	 * IMPORTANTE: usar java.sql.Time para BD
	 */
	protected LocalDateTime fecha;
	
	
	public Anotacion(String c, boolean p) {
		publico = p;
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
	
	
	public  boolean esPublico() {
		return publico;
	}
	
	public String getComentario(){
		return comentario;
	}
	
	public void setComentario(String c) {
		fecha = LocalDateTime.now();
		comentario = c;
	}
	
	
}
