package principal;

import java.io.IOException;
import controladores.*;
import gestorComics.Galeria;
import gestorComics.IGaleria;
import gui.*;

public class NOBDGestorComics {

	public static void main(String[] args) throws IOException {
		
		IGaleria galeria = new Galeria();
		IVentanaGaleria vg = new VentanaGaleria(galeria);

		vg.setTitulo("Gestor de Cómics (Modo OFFLINE)");
		CtrVentanaGaleria ctr = new CtrVentanaGaleria(galeria, vg);
		vg.setControlador(ctr);

	

	}

}
