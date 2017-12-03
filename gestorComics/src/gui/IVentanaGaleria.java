package gui;

import java.awt.event.ActionListener;

import gestorComics.Obra;
/*
 * Ventana de obras siguiendo el patrón singleton
 */
public interface IVentanaGaleria{

public static final String ADD_VINETA="ADD_VINETA";	
public static final String ADD_COMIC = "ADD_COMIC";



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

public void refrescar();


}
