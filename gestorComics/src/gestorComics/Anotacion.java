package gestorComics;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Anotacion implements Historico, Sincronizable {

	protected String comentario;
	
	private Comic comic;
	private Vineta vineta;
	private Vineta boceto;
	private IBD bd;
	
	private boolean publico;
	/*
	 * IMPORTANTE: usar java.sql.Time para BD
	 */
	protected LocalDateTime fecha;
	
	public Anotacion(Comic c, Vineta v, Vineta b,  boolean p) {
		comic = c; vineta=v; boceto = b; publico = p;
	}
	
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

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conectar(IBD b) {
		bd = b;
	}

	@Override
	public void subir() {
		bd.insertarAnotacion(comic, vineta, boceto, comentario);
		
	}

	@Override
	public void retirar() {
		bd.borrarAnotacion(comic, vineta, boceto);
		
	}
	
	
}
