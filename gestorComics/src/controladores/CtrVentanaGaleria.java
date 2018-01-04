package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gestorComics.Comic;
import gestorComics.IGaleria;
import gestorComics.Obra;
import gui.*;


public class CtrVentanaGaleria implements ActionListener, IObserver {
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
		
		}

	}
	
	
	private void anadirComic() {
		System.out.println("Añadir Comic");
		new AnadirComic(galeria);
		for(Comic c: galeria.getComics()) {
			c.registrar(this);
		}
	}
	
	private void anadirVineta() {
		System.out.println("Añadir Viñeta");
		new AnadirVineta(galeria);
	}

	@Override
	public void actualizar() {
		ventana.refrescar();
	}
	

	
}
