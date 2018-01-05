package gestorComics;

import java.util.Date;

import gestorComics.*;

public class Alarma extends Thread{

	int identificador;
	
	Comic comic;
	Vineta vineta;
	MedioComunicacion medio;
	Date fecha;
	
	public Alarma(int i, Comic c, Vineta v, MedioComunicacion m, Date f) {
		
		identificador = i;
		
		comic = c;
		vineta  = v;
		medio = m;
		fecha = f;
	}
	
	@Override
	public String toString() {
		String res = "";
		
		res = "Tiene que presentar la viñeta '" + vineta.getNombre() 
		+ "' perteneciente al comic '" + comic.getNombre() 
		+ "' a las " + "' para el medio de difusion '" + medio.getNombre() + "'.";
		
		return res;
	}
	
	@Override
	public void run() {
		while(!listo()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(listo()) {
			Activar();
		}
	}
	
	private boolean listo() {
		boolean res = false;
		
		return res;
	}
	
	private void Activar() {
		
		
		ManagerAlarmas.instance().removeAlarma(this);
	}
	
	public int getIdentificador() {
		return identificador;
	}
	
	public Date getFecha() {
		return fecha;
	}
}
