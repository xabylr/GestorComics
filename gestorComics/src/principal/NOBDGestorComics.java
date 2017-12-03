package principal;

import java.io.IOException;
import controladores.*;
import gui.*;

public class NOBDGestorComics {

	public static void main(String[] args) throws IOException {
		IVentanaGaleria vo = VentanaGaleria.getVentana() ;
		vo.setTitulo("Gestor de Cómics (Modo OFFLINE)");
		CtrMenuPrincipal ctr = new CtrMenuPrincipal(vo);
		vo.setControlador(ctr);

	

	}

}
