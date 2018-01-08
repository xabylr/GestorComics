package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import gestorComics.Anotacion;
import gestorComics.Comic;
import gestorComics.Obra;
import gestorComics.Vineta;

import java.awt.BorderLayout;

/*
 * Contiene una lista de anotaciones provenientes del modelo
 * Dentro de esta clase hay 
 */
@SuppressWarnings("serial")
public class PaneAnotaciones extends JScrollPane {

	JPanel panelAnotaciones; //panel descendente de anotaciones
	GridBagConstraints gbc ;
	
	WidgetAnotacion wAPublica;
	WidgetAnotacion wAPrivada;
	
	Comic comic;
	Vineta vineta;
	
	public PaneAnotaciones(Comic c, Vineta v) {
		comic =c;
		vineta = v;
		
		
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelAnotaciones = new JPanel();
		gbc = new GridBagConstraints();
	
		setViewportView(panelAnotaciones);
		//Hacemos un poco más rápido el scroll
		//getVerticalScrollBar().setUnitIncrement(16);
		
		
		//Código para añadir recuadrobs (de momento inerte) para añadir comentarios
		gbc.gridy = panelAnotaciones.getComponentCount();
		wAPublica = new WidgetAnotacion(comic, vineta, true);
		
		wAPrivada = new WidgetAnotacion(comic, vineta, false);
		panelAnotaciones.setLayout(new BorderLayout(0, 0));
		
		panelAnotaciones.add(wAPublica, BorderLayout.NORTH);
		panelAnotaciones.add(wAPrivada, BorderLayout.SOUTH);
				
		
	}
	
	
	public void refrescar() {
		validate();
	}
	
	public void setAnotacion(Anotacion anotacion) {
		if (anotacion.esPublico()) wAPublica = new WidgetAnotacion(anotacion);
		else wAPrivada = new WidgetAnotacion(anotacion);
		validate();
	}
	
	
	public void delAnotacionPublica() {
		wAPublica = new WidgetAnotacion(comic, vineta, true);
		refrescar();
	}
	
	public void delAnotacionPrivada() {
		wAPrivada = new WidgetAnotacion(comic, vineta, false);
		refrescar();
	}
	
	
}
