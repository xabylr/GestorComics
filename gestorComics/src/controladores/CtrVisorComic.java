package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gestorComics.CambioNombre;
import gestorComics.ComprobadoBorrado;
import gestorComics.IComprobadoBorrado;
import gestorComics.INombreCambiable;
import gui.IVisorComic;
import gui.VisorComic;

public class CtrVisorComic implements ActionListener, INombreCambiable{
	
	IVisorComic ventana;
	
	public CtrVisorComic(VisorComic vc) {
		ventana = vc;
	}

	@Override
	public void setNombre(String name) {
		if(name == null) return;
		ventana.getComic().setNombre(name);
		ventana.setNombre(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if(str.equals(IVisorComic.CAMBIARNOMBRE)) {
			CambioNombre cambionombre = new CambioNombre();
			CtrCambioNombre ctrcambionombre = new CtrCambioNombre(this, cambionombre);
			
			cambionombre.controlador(ctrcambionombre);
		} else if(str.equals(IVisorComic.BORRARCOMIC)) {
			ComprobadoBorrado comprobadoborrado = new ComprobadoBorrado(ventana.getComic());
			
			comprobadoborrado.setTexto("¿Está seguro que desea borrar el cómic "+ventana.getComic()+"?");
		}
	}


}
