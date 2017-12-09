package principal;

import java.io.IOException;
import controladores.*;
import gestorComics.BD;
import gestorComics.Galeria;
import gestorComics.IBD;
import gestorComics.IGaleria;
import gui.*;

public class NOBDGestorComics {

	public static void main(String[] args) throws IOException {
		
		IGaleria galeria = new Galeria();
		IVentanaGaleria vg = new VentanaGaleria(galeria);

		vg.setTitulo("Gestor de Cómics (Modo OFFLINE)");
		CtrMenuPrincipal ctr = new CtrMenuPrincipal(galeria, vg);
		vg.setControlador(ctr);

	

	}

}
