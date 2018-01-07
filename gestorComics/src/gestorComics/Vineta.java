package gestorComics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.RuntimeErrorException;

import excepciones.ExcepcionBD;
import gui.Observador;

public class Vineta extends Obra implements Observable{
	
	private IBD bd;
	private Image imagen;
	private Collection<Observador> observadores;

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
		notificarTodos();
	}
	
	public Vineta() {
		observadores = new ArrayList<>();
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
	public void addAnotacionPrivada(AnotacionPrivada a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AnotacionPrivada> getAnotacionesPrivadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delAnotacionPrivada(AnotacionPrivada a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Anotacion> getAnotaciones() {
		List<Anotacion> list = new ArrayList<Anotacion>();
		return list;
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
