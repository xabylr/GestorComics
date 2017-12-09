package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import gestorComics.Anotacion;
import gestorComics.Obra;

/*
 * Contiene una lista de anotaciones provenientes del modelo
 * Dentro de esta clase hay 
 */
@SuppressWarnings("serial")
public class PaneAnotaciones extends JScrollPane {

	JPanel panelAnotaciones; //panel descendente de anotaciones
	List<Anotacion> listaAnotaciones; //almacenamos el origen de las obras
	
	GridBagLayout gbl;
	GridBagConstraints gbc ;
	
	public PaneAnotaciones(List<Anotacion> list) {
		listaAnotaciones=list;
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelAnotaciones = new JPanel();
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		panelAnotaciones.setLayout(gbl);
	
		setViewportView(panelAnotaciones);
		//Hacemos un poco más rápido el scroll
		//getVerticalScrollBar().setUnitIncrement(16);
		
		
		//Código para añadir recuadrobs (de momento inerte) para añadir comentarios
		addAnotaciones(listaAnotaciones);
		gbc.gridy = panelAnotaciones.getComponentCount();
				panelAnotaciones.add(new WidgetAnotacion(), gbc);
	}
	
	
	public void refrescar() {
		panelAnotaciones.removeAll();
		addAnotaciones(listaAnotaciones);
	}
	
	public void addAnotacion(Anotacion anotacion) {
		addAnotacionNoValidate(anotacion);
		validate();
	}
	
	private void addAnotacionNoValidate(Anotacion anotacion) {
		gbc.gridy = panelAnotaciones.getComponentCount();
		
		panelAnotaciones.add(new WidgetAnotacion(anotacion), gbc);

		
	}
	public void addAnotaciones(List<Anotacion> l) {
			Iterator<Anotacion> it = l.iterator();
			while(it.hasNext()) {
				addAnotacionNoValidate(it.next() );
			}
		
		validate();
	}
	
	public void delAnotacion(WidgetAnotacion wa) {
		//TODO crear un equals de la miniatura
		System.err.println("HAY QUE CREAR UN EQUALS DE OBRA PARA PODER ENCONTRAR LA OBRA");
		panelAnotaciones.remove(wa);
		validate();
	}
}
