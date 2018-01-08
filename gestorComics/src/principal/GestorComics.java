package principal;

import java.io.IOException;
import controladores.*;
import gestorComics.BD;
import gestorComics.Galeria;
import gestorComics.IBD;
import gestorComics.IGaleria;
import gestorComics.ManagerAlarmas;
import gui.*;

public class GestorComics {

	public static void main(String[] args) throws IOException {
			IBD bd = new BD();
			IGaleria galeria = new Galeria(bd);
			
			IVentanaGaleria vg = new VentanaGaleria(galeria);
			
			vg.setTitulo("Gestor de Cómics (Segunda iteración)");
			CtrVentanaGaleria ctr = new CtrVentanaGaleria(galeria, vg);
			vg.setControlador(ctr);

			
			ManagerAlarmas.instance().setBD(bd);
			
	}

}
