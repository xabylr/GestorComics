package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gestorComics.Comic;
import gestorComics.IGaleria;
import gestorComics.Obra;

import javax.imageio.ImageIO;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VentanaGaleria extends JFrame implements IVentanaGaleria{

	
	private JButton BTNAnadirComic;
	private JButton BTNAnadirVineta;
	private PaneObras paneObras;
	private IGaleria galeria;
	
	
	private final static int ANCHURA = 850;
	private final static int ALTURA = 600;
	private JButton btnAadirAlarma;
	private JButton btnAadirMedio;

	
	@SuppressWarnings("unchecked")
	public VentanaGaleria(IGaleria g) {
		galeria = g;
		setTitle("Gestor de cómics");
		
		try {
			setIconImage(ImageIO.read(new File("src/res/icon.png")));
		} catch (IOException e) {}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel PanelBotones = new JPanel();
		getContentPane().add(PanelBotones, BorderLayout.SOUTH);
		
		btnAadirMedio = new JButton("Añadir Medio");
		PanelBotones.add(btnAadirMedio);
		
		btnAadirAlarma = new JButton("Añadir Alarma");
		PanelBotones.add(btnAadirAlarma);
		
		BTNAnadirVineta = new JButton("Añadir Viñeta");
		PanelBotones.add(BTNAnadirVineta);
		
		BTNAnadirComic = new JButton("Añadir Comic");
		PanelBotones.add(BTNAnadirComic);
		
		paneObras = new PaneObras( (List<Obra>) (List<?>) galeria.getComics(), null );
		
		getContentPane().add(paneObras, BorderLayout.CENTER);
		
		setSize(ANCHURA, ALTURA);
		
		//CENTRADO DE VENTANA
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
		
		setVisible(true);
	}
	
	
	public void setControlador(ActionListener ctr) {
		BTNAnadirComic.setActionCommand(IVentanaGaleria.ADD_COMIC);
		BTNAnadirComic.addActionListener(ctr);
		BTNAnadirVineta.setActionCommand(IVentanaGaleria.ADD_VINETA);
		BTNAnadirVineta.addActionListener(ctr);
		btnAadirAlarma.setActionCommand(IVentanaGaleria.ALARMA);
		btnAadirAlarma.addActionListener(ctr);
		btnAadirMedio.setActionCommand(IVentanaGaleria.MEDIO);
		btnAadirMedio.addActionListener(ctr);
	}
	
	@Override
	public void refrescar() {
		paneObras.refrescar();
		validate();
	}
	
	@Override
	public void addObra(Obra obra) {
		paneObras.addObra(obra);
	}
	
	@Override
	public void addObras(List<Obra> lobras){
		paneObras.addObras(lobras);
	}

	public void delObra(Obra obra) {
		paneObras.delObra(obra);
	}

	@Override
	public void setTitulo(String titulo) {
		setTitle(titulo);
		
	}
	
	@Override
	public void mensajeError(String error) {
		//TODO hacer algo
	}
	





}
