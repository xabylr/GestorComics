package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gestorComics.IBorradoComprobable;
import gestorComics.IComprobadoBorrado;

public class CtrComprobadoBorrado implements ActionListener {

	
	IBorradoComprobable comprobadoborrado;
	IComprobadoBorrado vista;
	
	public CtrComprobadoBorrado(IBorradoComprobable cb, IComprobadoBorrado v) {
		comprobadoborrado = cb;
		vista = v;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals(IComprobadoBorrado.ACEPTAR)) {
			comprobadoborrado.Borrar();
			
			vista.dispose();
		}
		
		if(cmd.equals(IComprobadoBorrado.CANCELAR)) {
			vista.dispose();
		}
	}
}
