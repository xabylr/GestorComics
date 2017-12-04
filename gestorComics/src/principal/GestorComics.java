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
			IGaleria galeria = Galeria.getGaleria();
			IBD bd = BD.getBD();
			try {
				galeria.conectar(bd);
			} catch (SQLException e) {
				System.err.println("ERROR AL CONECTAR A LA BBDD");
				e.printStackTrace();
			}			
			IVentanaGaleria vo = VentanaGaleria.getVentana();
			
			vo.setTitulo("Gestor de Cómics (Primera iteración)");
			CtrMenuPrincipal ctr = new CtrMenuPrincipal(vo);
			vo.setControlador(ctr);

			
	}

}
