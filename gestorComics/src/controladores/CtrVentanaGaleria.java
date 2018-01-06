package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gestorComics.Comic;
import gestorComics.IGaleria;
import gestorComics.Obra;
import gui.*;


public class CtrVentanaGaleria implements ActionListener, Observador {
	IVentanaGaleria ventana;
	IGaleria galeria;

	public CtrVentanaGaleria(IGaleria g, IVentanaGaleria v) {
		galeria = g;
		ventana = v;
		
		galeria.conectarGUI(v);
		//al cambiar la colección se refrescará la vista
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
		switch(e.getActionCommand()) {
		case IVentanaGaleria.ADD_COMIC:
			anadirComic();
			break;
			
		case IVentanaGaleria.ADD_VINETA:
			anadirVineta();
			break;
			
		case IVentanaGaleria.ALARMA:
			anadirAlarma();
			break;
		}

	}
	
	
	private void anadirComic() {
		System.out.println("Añadir Comic");
		new AnadirComic(galeria);
		for(Comic c: galeria.getComics()) {
			if(!c.observadoPor(this)) c.registrar(this);
		}
	}
	
	private void anadirVineta() {
		System.out.println("Añadir Viñeta");
		new AnadirVineta(galeria);
	}
	
	private void anadirAlarma() {
		System.out.println("Añadir alarma"); //DONDE SE CREA EL MANAGER
		AnadirAlarma alarma = new AnadirAlarma(galeria);
		CtrAnadirAlarma ctr = new CtrAnadirAlarma(alarma);
		alarma.controlador(ctr);
	}

	@Override
	public void notificar() {
		ventana.refrescar();
	}


	
}