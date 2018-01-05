package controladores;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import gestorComics.ICambioNombre;
import gestorComics.INombreCambiable;

public class CtrCambioNombre implements ActionListener{

	
	INombreCambiable nombrecambiable;
	ICambioNombre vista;
	
	public CtrCambioNombre(INombreCambiable nc,ICambioNombre v) {
		nombrecambiable = nc;
		vista = v;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals(ICambioNombre.ACEPTAR)) {
			nombrecambiable.setNombre(vista.getNombre());
			
			vista.dispose();
		}
		
		if(cmd.equals(ICambioNombre.CANCELAR)) {
			vista.dispose();
		}
	}

}
