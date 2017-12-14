package principal;

import java.io.IOException;
import controladores.*;
import gestorComics.BD;
import gestorComics.Galeria;
import gestorComics.IBD;
import gestorComics.IGaleria;
import gestorComics.Vineta;
import gui.*;

public class Mockup {

	public static void main(String[] args) throws IOException {
			IBD bd = new BD();
			IGaleria galeria = new Galeria(bd);
			
			IVentanaGaleria vg = new VentanaGaleria(galeria);
	
			vg.setTitulo("Gestor de cómics (MOCKUP)");
			CtrVentanaGaleria ctr = new CtrVentanaGaleria(galeria, vg);
			vg.setControlador(ctr);
			
			vg.addObra(new Vineta("Viñeta Vacía"));
			

	}

}
