

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
import gestorComics.CambioNombre;
import gestorComics.ComprobadoBorrado;
import gestorComics.IBorradoComprobable;
import gestorComics.ICambioNombre;
import gestorComics.IComprobadoBorrado;
import gestorComics.IImagenCambiable;
import gestorComics.INombreCambiable;
import gui.IVisorVineta;

public class CtrVentanaVineta implements ActionListener, INombreCambiable, IBorradoComprobable, IImagenCambiable {

	IVisorVineta ventana;

	
	public CtrVentanaVineta(IVisorVineta ventanaVineta) {
		ventana = ventanaVineta;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals(IVisorVineta.CANCELAR)) {
			((Component) ventana).dispatchEvent(new WindowEvent((Window) ventana, WindowEvent.WINDOW_CLOSING));
		}
		
		if(cmd.equals(IVisorVineta.CAMBIARNOMBRE)) {
			CambioNombre cambionombre = new CambioNombre();
			CtrCambioNombre ctrcambionombre = new CtrCambioNombre(this, cambionombre);
			
			cambionombre.controlador(ctrcambionombre);
		}
		
		if(cmd.equals(IVisorVineta.BORRAR)) {
			IComprobadoBorrado comprobadoborrado = new ComprobadoBorrado();
			
			CtrComprobadoBorrado strcomprobadoborrado = new CtrComprobadoBorrado(this, comprobadoborrado);
			
			comprobadoborrado.controlador(strcomprobadoborrado);
			
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

	@Override
	public void setNombre(String name) {
		if(name == null) return;
		ventana.getViñeta().setNombre(name);
		ventana.SetNombre(name);
	}
	
	

	@Override
	public void Borrar() {
		//BORRAR DE COMIC
		ventana.dispose();
	}

	@Override
	public void setImagen(Image img) {
		ventana.getViñeta().setImagen(img);
		ventana.SetImagen(img);
	}

}