	package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.JPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import controladores.CtrVentanaVineta;
import excepciones.RecursoNoEncontrado;
import gestorComics.*;

import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
/*
 * Esta clase es con la que trabaja la vista y tiene una obra asociada
 */
@SuppressWarnings("serial")
public class Miniatura extends JPanel implements Visualizable{
	
	protected List<Observador>listaSuscritos;
	
	public static String RUTA_IMAGEN_POR_DEFECTO = "src/res/imagen_no_disponible.png";
	public static String NOMBRE_POR_DEFECTO = "Sin nombre";
	
	static Image IMAGEN_POR_DEFECTO;
	
	//Altura y anchura de la imagen
	static int ALT_IMG = 150;
	static int ANCH_IMG = 150;
	//Altura y anchura de la descripcion
	static int ALT_DESCR = 30;
	static int ANCH_DESCR = 100;
	
	//Separacion del borde
	static int sepborde = 10;
	
	static {
		try {
			IMAGEN_POR_DEFECTO = ImageIO.read(new File(RUTA_IMAGEN_POR_DEFECTO) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Image imagen;
	String nombre;
	Obra obra;
	Comic comic; //si fuera una viñeta la miniatura
	
	public Miniatura(){

		imagen = IMAGEN_POR_DEFECTO;
		nombre = NOMBRE_POR_DEFECTO;
		
		procesarMarco();
	}
	
	public Miniatura(Obra o, Comic c){
		listaSuscritos = new ArrayList<>();
		
		//TODO reducir tamaño de la imagen
		imagen = o.vistaPrevia();
		nombre = o.getNombre();
		obra = o;
		
		comic = c;
		
		procesarMarco();
	}
	
	private void procesarMarco(){
		
		if (imagen == null) imagen = IMAGEN_POR_DEFECTO;
		if (nombre == null) nombre = NOMBRE_POR_DEFECTO;
		
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
				JPanel panelMiniatura = new JPanel();
				add(panelMiniatura);
		
				JLabel lminiatura = new JLabel(
						new ImageIcon(imagen.getScaledInstance(
								ANCH_IMG, ALT_IMG, Image.SCALE_DEFAULT)));
				
				
				panelMiniatura.add(lminiatura);
			
				JPanel panelDescripcion = new JPanel();
				add(panelDescripcion);

				JLabel ldescripcion = new JLabel(nombre);
				ldescripcion.setHorizontalTextPosition(SwingConstants.CENTER);
				ldescripcion.setHorizontalAlignment(SwingConstants.CENTER);
				panelDescripcion.add(ldescripcion);
				ldescripcion.setSize(ANCH_DESCR, ALT_DESCR);
				
			
		
		//setSize(new Dimension(ANCH_IMG + ANCH_DESCR + sepborde, ALT_IMG + ALT_DESCR + sepborde));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {	
				seleccionado();
			}
		});
	}
	
	public Obra getObra(){
		return obra;
	}
	
	@Override
	public Image vistaPrevia() {
		return imagen;
	}


	public void seleccionado() {
		
		try {
			mostrarObra();
		}catch (RecursoNoEncontrado e) {
			//TODO mostrar error en ventana
			System.err.println("RECURSO NO ENCONTRADO");
		}
		
		
	}
	
	
	public void mostrarObra() {
		if(obra instanceof Vineta) {
			VentanaVineta ventana = new VentanaVineta((Vineta)obra, comic);
			CtrVentanaVineta ctr = new CtrVentanaVineta(ventana, comic);
			ventana.controlador(ctr);
		}else if(obra instanceof Comic){
			new VentanaComic( (Comic) obra);

		}
	}
	
	

	


}
