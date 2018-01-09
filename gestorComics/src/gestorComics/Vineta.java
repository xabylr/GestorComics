package gestorComics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import excepciones.ExcepcionBD;
import gui.Observador;

public class Vineta extends Obra implements Observable, Anotable{
	
	private IBD bd;
	private Image imagen;
	private Collection<Observador> observadores;
	private Anotacion anotacionPrivada;
	private Map<Comic, Anotacion> anotacionesPublicas;

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image i) {
		imagen = i;
		if(bd!=null) bd.actualizarImagenVineta(this);
		notificarTodos();
	}
	
	@Override
	public void setNombre(String n) {
		super.setNombre(n);
		bd.renombrarVineta(this, n);
		notificarTodos();
	}
	
	public Vineta() {
		observadores = new ArrayList<>();
		anotacionesPublicas = new HashMap<>();
	}

	public Vineta(String n) {
		this();
		nombre = n;
	}
	
	public Vineta(Image i) {
		this();
		imagen = i;
	}
	
	public Vineta(Image i, String n) {
		this();
		imagen = i;
		nombre = n;
	}

	
	@Override
	public int compareTo(Obra o) {
		return getNombre().compareTo(o.getNombre());
	}


	@Override
	public Image vistaPrevia() {
		return imagen;
	}

	@Override
	public String toString() {
		return "Viñeta: "+nombre+" (ID "+ID+")";
	}

	@Override
	public void conectar(IBD b) {
		bd = b;
		
	}
	
	@Override
	public void subir() {
		System.out.println("NO SE HACE NADA PARA SINCRONIZAR UNA VIÑETA AÚN");
	}


	@Override
	public void setAnotacion(Anotacion a) {
		if(a.esPublico()) anotacionesPublicas.put(a.getComic(), a);
		else anotacionPrivada = a;
		
		if(bd!=null)
		bd.insertarAnotacion(a.getComic(), this, null, a.getComentario());
	}

	@Override
	public Anotacion getAnotacionPrivada() {
		if(bd != null && anotacionPrivada == null) {
			anotacionPrivada = bd.obtenerAnotacion(null, this, null);
			anotacionPrivada.conectar(bd);
		}
		return anotacionPrivada;
	}
	
	@Override
	public Anotacion getAnotacionPublica(Comic c) {
		if(bd != null && anotacionesPublicas.get(c) == null ) {
			anotacionesPublicas.put(c, bd.obtenerAnotacion(c, this, null));
			anotacionesPublicas.get(c).conectar(bd);
		}
			
			
		return anotacionesPublicas.get(c);
	}

	@Override
	public void delAnotacionPublica(Comic c){
		anotacionesPublicas.remove(c);
	}
	
	@Override
	public void delAnotacionPrivada() {
		anotacionPrivada = null;
	}
	
	
	@Override
	public void retirar() {
		System.out.println("ERROR, usar retirarDe en su lugar para borrar viñeta de BD");
		throw new RuntimeException("ERROR, usar retirarDe en su lugar para borrar viñeta de BD");
	}

	void retirarDe(Comic c) {
		if(bd!=null) bd.borrarVineta(this.getID(), c.getID());
		
		notificarTodosBorrado();
		
	}

	@Override
	public void registrar(Observador o) {
		observadores.add(o);
	}

	@Override
	public void darbBaja(Observador o) {
		observadores.remove(o);
	}

	@Override
	public boolean observadoPor(Observador o) {
		return observadores.contains(o);
	}

	@Override
	public void notificarTodos() {
		for (Observador o : observadores) o.notificar();
	}

	@Override
	public void notificarTodosBorrado() {
		for (Observador o : observadores) o.notificarBorrado(this);
	}
	

}
