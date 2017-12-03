package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.*;


public class CtrMenuPrincipal implements ActionListener {
	IVentanaGaleria ventana;

	public CtrMenuPrincipal(IVentanaGaleria v) {
		ventana = v;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String cmd = e.getActionCommand();
		
		if(cmd.equals(IVentanaGaleria.ADD_COMIC)){
			System.out.println("Añadir Comic");
			anadirComic();
		}
		else if(cmd.equals(IVentanaGaleria.ADD_VINETA)){
			System.out.println("Añadir Viñeta");
			anadirVineta();
		}

	}
	
	
	private void anadirComic() {
		new AnadirComic();
	}
	
	private void anadirVineta() {
		new AnadirVineta();
	}
	

	
}
