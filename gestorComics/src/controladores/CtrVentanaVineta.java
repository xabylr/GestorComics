package controladores;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import gui.IVisorVineta;

public class CtrVentanaVineta implements ActionListener {

	IVisorVineta ventana;

	
	public CtrVentanaVineta(IVisorVineta ventanaVineta) {
		ventana = ventanaVineta;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(IVisorVineta.CANCELAR)) {
			((Component) ventana).dispatchEvent(new WindowEvent((Window) ventana, WindowEvent.WINDOW_CLOSING));
		}
		
	}

}
