package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gestorComics.ComprobadoBorrado;
import gestorComics.IComprobadoBorrado;
import gui.IVisorComic;
import gui.VentanaComic;

public class CtrVentanaComic implements ActionListener{
	
	IVisorComic ventana;
	
	public CtrVentanaComic(VentanaComic vc) {
		ventana = vc;
	}


	public void setNombre(String name) {
		if(name == null) return;
		ventana.getComic().setNombre(name);
		ventana.setNombre(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if(str.equals(IVisorComic.CAMBIARNOMBRE)) {
			 new gui.CambioNombre(ventana.getComic());

		} else if(str.equals(IVisorComic.BORRARCOMIC)) {
			ComprobadoBorrado comprobadoborrado = new ComprobadoBorrado(ventana.getComic(), null);
			
			comprobadoborrado.setTexto("¿Está seguro que desea borrar el cómic "+ventana.getComic()+"?");
		}
	}


}
