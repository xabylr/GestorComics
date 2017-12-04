package principal;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import controladores.*;
import gestorComics.BD;
import gestorComics.Galeria;
import gestorComics.IBD;
import gestorComics.Vineta;
import gui.*;

public class Mockup {

	public static void main(String[] args) throws IOException {
			Galeria galeria = Galeria.getGaleria();
			IBD bd = BD.getBD();
			try {
				galeria.conectar(bd);
			} catch (SQLException e) {
				System.err.println("ERROR AL CONECTAR A LA BBDD");
				e.printStackTrace();
			}
		
				
			IVentanaGaleria vo = VentanaGaleria.getVentana() ;
			vo.setTitulo("Gestor de cómics (MOCKUP)");
			CtrMenuPrincipal ctr = new CtrMenuPrincipal(vo);
			vo.setControlador(ctr);
			
			vo.addObra(new Vineta("Viñeta Vacía"));
			

	}

}
