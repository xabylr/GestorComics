package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gestorComics.*;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ScrollPaneConstants;
/*
 * Sirve tanto para ver la galería de obras como para ver las viñetas de un cómic
 */
@SuppressWarnings("serial")
public class PaneObras extends JScrollPane {
	
	public static final int NCOL=5;

	JPanel panelObras; //parrilla con objetos de miniaturas
	List<Obra> listaObras; //almacenamos el origen de las obras
	
	GridBagLayout gbl;
	GridBagConstraints gbc ;
	
	public PaneObras(List<Obra> list) {
		listaObras=list;
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelObras = new JPanel();
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		panelObras.setLayout(gbl);
	
		setViewportView(panelObras);
		//Hacemos un poco más rápido el scroll
		getVerticalScrollBar().setUnitIncrement(16);
		
		addObras(listaObras);
	}
	
	
	public void refrescar() {
		panelObras.removeAll();
		panelObras.repaint();
		addObras(listaObras);
	}
	
	public void addObra(Obra obra) {
		addObraNoValidate(obra);
		validate();
	}
	
	private void addObraNoValidate(Obra obra) {
		gbc.gridx = panelObras.getComponentCount()%NCOL;
		gbc.gridy = panelObras.getComponentCount()/NCOL;
		
		panelObras.add(new Miniatura(obra), gbc );

		
	}
	public void addObras(List<Obra> l) {
			Iterator<Obra> it = l.iterator();
			while(it.hasNext()) {
				addObraNoValidate(it.next() );
			}
		
		validate();
	}
	
	public void delObra(Obra obra) {
		//TODO crear un equals de la miniatura
		System.err.println("HAY QUE CREAR UN EQUALS DE OBRA PARA PODER ENCONTRAR LA OBRA");
		panelObras.remove(new Miniatura(obra) );
		validate();
	}
}
