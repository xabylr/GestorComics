
package controladores;

import java.awt.Component;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import excepciones.ExcepcionUsuario;
import gestorComics.Comic;
import gestorComics.ComprobadoBorrado;
import gestorComics.Observable;
import gestorComics.Vineta;
import gui.IVisorVineta;
import gui.Observador;

public class CtrVentanaVineta implements ActionListener, Observador {

	IVisorVineta ventana;
	Vineta vineta;
	Comic comic;

	
	public CtrVentanaVineta(IVisorVineta ventanaVineta, Comic c) {
		ventana = ventanaVineta;
		vineta = ventanaVineta.getVineta();
		vineta.registrar(this);
		
		comic = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals(IVisorVineta.CANCELAR)) {
			((Component) ventana).dispatchEvent(new WindowEvent((Window) ventana, WindowEvent.WINDOW_CLOSING));
		}
		
		if(cmd.equals(IVisorVineta.CAMBIARNOMBRE)) {
			new gui.CambioNombre(vineta);			
		}
		
		if(cmd.equals(IVisorVineta.BORRAR)) {
			ComprobadoBorrado comprobadoborrado = new ComprobadoBorrado(vineta, comic);
			
			
			comprobadoborrado.setTexto("¿Está seguro que desea borrar esta viñeta?");
		}
		
		if(cmd.equals(IVisorVineta.CAMBIARIMAGEN)) {
			JFileChooser explorador = new JFileChooser();		
			int eleccion = explorador.showOpenDialog(new JFrame());		
			if (eleccion != JFileChooser.APPROVE_OPTION)
				throw new CancellationException();
			File archivoEscogido = explorador.getSelectedFile();
			try {
				Image img = ImageIO.read(archivoEscogido);
				setImagen(img);
			}catch (IllegalArgumentException e1) {
				throw new ExcepcionUsuario("Elige una imagen válida");
			} catch (IOException e1) {
				throw new ExcepcionUsuario("Error de lectura");
			}
			
		}
		
	}


	public void setImagen(Image img) {
		vineta.setImagen(img);
		ventana.SetImagen(img);
	}
	
	
	

	@Override
	public void notificar() {
		ventana.setNombre(vineta.getNombre());
		ventana.SetImagen(vineta.getImagen());
	}

	@Override
	public void notificarBorrado(Observable o) {
		ventana.dispose();
	}

}