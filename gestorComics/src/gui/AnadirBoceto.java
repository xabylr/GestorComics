package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import excepciones.ExcepcionUsuario;
import gestorComics.Comic;
import gestorComics.IGaleria;
import gestorComics.Vineta;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class AnadirBoceto extends JFrame{
	
	private JLabel lblAlert;
	
	private JTextField tfNombre;
	private Image imagen;
	private Miniatura miniatura;
	private IGaleria galeria;
	private Comic comic;
	
	private List<ContenedorComic> listacomics;
	
	private Vineta nuevoBoceto;
	
	public AnadirBoceto(Comic c) {
		comic = c;
		listacomics = new ArrayList<ContenedorComic>();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelSelComic = new JPanel();
		getContentPane().add(panelSelComic, BorderLayout.NORTH);
		panelSelComic.setLayout(new BoxLayout(panelSelComic, BoxLayout.Y_AXIS));
		
		JPanel panelDesc = new JPanel();
		panelSelComic.add(panelDesc);
		
		JLabel lbSelComic = new JLabel("Elige una imagen para el boceto");
		panelDesc.add(lbSelComic);
		lbSelComic.setHorizontalTextPosition(SwingConstants.CENTER);
		
		JPanel panelNombreVineta = new JPanel();
		panelSelComic.add(panelNombreVineta);
		
		JLabel lblNombreVineta = new JLabel("Nombre:");
		panelNombreVineta.add(lblNombreVineta);
		
		tfNombre = new JTextField();
		panelNombreVineta.add(tfNombre);
		tfNombre.setColumns(10);
		
		JPanel panelSubir = new JPanel();
		panelSelComic.add(panelSubir);
		
		JButton btnSelImg = new JButton("Seleccionar Imagen");
		
		
		
		panelSubir.setLayout(new BoxLayout(panelSubir, BoxLayout.Y_AXIS));
		
		panelSubir.add(btnSelImg);
		btnSelImg.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lblAlert = new JLabel("");
		lblAlert.setVisible(false);
		panelSubir.add(lblAlert);
		lblAlert.setForeground(Color.RED);
		
		JPanel panelBtn = new JPanel();
		getContentPane().add(panelBtn, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispatchEvent( new WindowEvent(AnadirBoceto.this, WindowEvent.WINDOW_CLOSING) );
			}
		});
		panelBtn.add(btnNewButton);
		JButton btnCrearVineta = new JButton("Crear Viñeta");
		panelBtn.add(btnCrearVineta);
		
		pack();
		
		//Botón seleccionar imagen
		btnSelImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		try {		
					JFileChooser explorador = new JFileChooser();		
					int eleccion = explorador.showOpenDialog(new JFrame());		
					if (eleccion != JFileChooser.APPROVE_OPTION)
						throw new CancellationException();
					File archivoEscogido = explorador.getSelectedFile();
					try {
						Image img = ImageIO.read(archivoEscogido);
						imagen=img;
					}catch (IllegalArgumentException e) {
						throw new ExcepcionUsuario("Elige una imagen válida");
					} catch (IOException e) {
						throw new ExcepcionUsuario("Error de lectura");
					}
					
					nuevoBoceto = new Vineta(imagen, tfNombre.getText());
					
					if(miniatura!=null) panelSubir.remove(miniatura);	
					miniatura = new Miniatura(nuevoBoceto, null);
					
					panelSubir.add(miniatura);
					pack();
					revalidate();
					lblAlert.setVisible(false);
					
		} catch (ExcepcionUsuario e) {
			if(miniatura!=null) panelSubir.remove(miniatura);	
			alert(e.getMessage());
		} catch (CancellationException e) {
			
		}
					
		}});
		
		
		//Botón crear Boceto
		btnCrearVineta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try {
					if(nuevoBoceto == null )
						throw new ExcepcionUsuario("Selecciona una imagen");

							comic.insertarBoceto(nuevoBoceto);
						
						//cerramos la ventana
						dispatchEvent( new WindowEvent(AnadirBoceto.this, WindowEvent.WINDOW_CLOSING) );
					
					}catch (ExcepcionUsuario e) {
						alert(e.getMessage());
					}		
			}
		});
		
		//CENTRADO DE VENTANA
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
	    
	    setVisible(true);
	    setTitle("Añadir boceto");
	}

	
	

	public void alert(String error) {
		lblAlert.setVisible(true);
		lblAlert.setText(error);
		pack();
		revalidate();
	}




	
}
