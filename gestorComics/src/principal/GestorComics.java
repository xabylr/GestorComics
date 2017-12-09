package principal;

import java.io.IOException;
import java.sql.SQLException;

import controladores.*;
import gestorComics.BD;
import gestorComics.Galeria;
import gestorComics.IBD;
import gestorComics.IGaleria;
import gui.*;

public class GestorComics {

	public static void main(String[] args) throws IOException {
			IBD bd = new BD();
			IGaleria galeria = new Galeria(bd);
			
			IVentanaGaleria vg = new VentanaGaleria(galeria);
			
			vg.setTitulo("Gestor de Cómics (Segunda iteración)");
			CtrMenuPrincipal ctr = new CtrMenuPrincipal(galeria, vg);
			vg.setControlador(ctr);

			
	}

}
