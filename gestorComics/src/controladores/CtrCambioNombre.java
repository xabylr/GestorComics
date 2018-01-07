package controladores;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import gestorComics.CambioNombre;
import gestorComics.INombreCambiable;

public class CtrCambioNombre implements ActionListener{

	
	INombreCambiable nombrecambiable;
	CambioNombre vista;
	
	public CtrCambioNombre(INombreCambiable nc, CambioNombre v) {
		nombrecambiable = nc;
		vista = v;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals(CambioNombre.ACEPTAR)) {
			nombrecambiable.setNombre(vista.getNombre());
			
			vista.dispose();
		}
		
		if(cmd.equals(CambioNombre.CANCELAR)) {
			vista.dispose();
		}
	}

}
