package principal;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import gestorComics.Anotacion;
import gestorComics.AnotacionPrivada;
import gestorComics.AnotacionPublica;
import gui.PaneAnotaciones;
import gui.WidgetAnotacion;

public class PruebaAnotaciones {

	public static void main(String[] args) {
		List<Anotacion> alist = new ArrayList<>();
		
		Anotacion aPublica = new AnotacionPublica("Esto es una anotación pública");
		WidgetAnotacion paPub = new WidgetAnotacion(aPublica);
		
		Anotacion aPrivada = new AnotacionPrivada("Esto es una anotación privada");
		WidgetAnotacion paPriv = new WidgetAnotacion(aPrivada);
		
		WidgetAnotacion paVacio = new WidgetAnotacion();
		
		alist.add(aPublica); alist.add(aPrivada);
		
		JFrame ventana = new JFrame("Ventana de prueba");
		ventana.setLayout(new FlowLayout());
		
		PaneAnotaciones panA = new PaneAnotaciones( alist);
		
		ventana.add(panA);
		
		/*
		alist.add(paPub);
		ventana.add(paPriv);
		ventana.add(paVacio);*/
		
		ventana.pack();
		
		ventana.setVisible(true);
		
		
	}

}
