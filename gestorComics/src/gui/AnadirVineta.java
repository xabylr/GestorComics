package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import excepciones.ExcepcionUsuario;
import gestorComics.Comic;
import gestorComics.Galeria;
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
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class AnadirVineta extends JFrame implements IAnadirVineta {
	
	private JLabel lblAlert;
	private JComboBox<Comic> listaComics;
	private JTextField tfNombre;
	private Image imagen;
	private Miniatura miniatura;
	
	public AnadirVineta() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelSelComic = new JPanel();
		getContentPane().add(panelSelComic, BorderLayout.NORTH);
		panelSelComic.setLayout(new BoxLayout(panelSelComic, BoxLayout.Y_AXIS));
		
		JPanel panelDesc = new JPanel();
		panelSelComic.add(panelDesc);
		
		JLabel lbSelComic = new JLabel("Selecciona un cómic o crea una viñeta suelta");
		panelDesc.add(lbSelComic);
		lbSelComic.setHorizontalTextPosition(SwingConstants.CENTER);
		
		JPanel panelSelSubir = new JPanel();
		panelSelComic.add(panelSelSubir);
		
		listaComics = new JComboBox<Comic>();
		
		listaComics.addItem(new Comic("<Suelto>"));
		
		JLabel lblCmic = new JLabel("Cómic: ");
		panelSelSubir.add(lblCmic);
		
		panelSelSubir.add(listaComics);
		
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
				dispatchEvent( new WindowEvent(AnadirVineta.this, WindowEvent.WINDOW_CLOSING) );
			}
		});
		panelBtn.add(btnNewButton);
		JButton btnCrearVineta = new JButton("Crear Viñeta");
		panelBtn.add(btnCrearVineta);
		
		
		pack();
		
		btnSelImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		try {
					
					JFileChooser explorador = new JFileChooser();
					
					int eleccion = explorador.showOpenDialog(new JFrame());
					
					if (eleccion != JFileChooser.APPROVE_OPTION)
						throw new ExcepcionUsuario("Elige un archivo");

					File archivoEscogido = explorador.getSelectedFile();
					try {
						Image img = ImageIO.read(archivoEscogido);
						imagen=img;
					}catch (IllegalArgumentException e) {
						throw new ExcepcionUsuario("Elige una irmagen válida");
					}
					
					
					if(miniatura!=null) panelSubir.remove(miniatura);
					
					miniatura = new Miniatura(new Vineta(imagen, tfNombre.getText()));
					panelSubir.add(miniatura);
					pack();
					revalidate();
					
					
					lblAlert.setVisible(false);
				} catch (ExcepcionUsuario e) {
					alert(e.getMessage());
					e.printStackTrace();
				}catch(IOException e) {
					alert("Error al seleccionar un archivo");
					e.printStackTrace();
				}
				
		}});
		
		
		btnCrearVineta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Vineta nuevaVineta = new Vineta(imagen, tfNombre.getText(), Galeria.getGaleria().nextID() );
				Comic aComic = getComic();
				if (aComic==null) {
					Galeria.getGaleria().insert(nuevaVineta);
					VentanaGaleria.getVentana().addObra(nuevaVineta);
				}
				else {
					aComic.addVineta(nuevaVineta);
					
					//Si el cómic no tenía portada, le ponemos la viñeta de portada
					if(aComic.getPortada() == null) {
						aComic.setPortada(nuevaVineta);
						VentanaGaleria.getVentana().refrescar();
					}
					
				System.out.println(nuevaVineta+" agregada con éxito "
				+(aComic==null? "suelta": "en "+aComic.getNombre()));
				}
				
				dispatchEvent( new WindowEvent(AnadirVineta.this, WindowEvent.WINDOW_CLOSING) );
					
			}
		});
		
		
		//CENTRADO DE VENTANA
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
	    
	    setVisible(true);
	    setTitle("Añadir viñeta");
	}

	@Override
	public void alert(String error) {
		lblAlert.setVisible(true);
		lblAlert.setText(error);
		pack();
		revalidate();
	}

	@Override
	public void addComic(Comic c) {
		listaComics.addItem(c);
	}

	@Override
	public Comic getComic() {
		//Crear viñeta suelta
		if(listaComics.getSelectedIndex()==0) return null;
		
		//Devolver cómic suelto
		return (Comic)listaComics.getSelectedItem();
	}


}
