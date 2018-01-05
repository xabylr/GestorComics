package gui;

import java.awt.event.ActionListener;
import java.util.List;

import gestorComics.Obra;
/*
 * Ventana de obras siguiendo el patrón singleton
 */
public interface IVentanaGaleria{

public static final String ADD_VINETA="ADD_VINETA";	
public static final String ADD_COMIC = "ADD_COMIC";
public static final String ALARMA = "ALARMA";



/*
 * Establece un controlador para pulsaciones de botones
 */
public void setControlador(ActionListener ctr);
	

public void setTitulo(String titulo);
/*
 * Añade una obra en la vista
 */
void addObra(Obra obra);
/*
 * Elimina una obra de la vista
 */
public void delObra(Obra obra);


/*
 * Refresca la lista de obras en pantalla en base a su lista interna
 */
public void refrescar();


void mensajeError(String error);


void addObras(List<Obra> lobras);


}
