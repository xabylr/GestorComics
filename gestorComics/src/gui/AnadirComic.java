package gui;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import gestorComics.Comic;
import gestorComics.IGaleria;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class AnadirComic extends JFrame{
	
	private JTextField tNombre;
	JButton btnAnadir;
	JButton btnCancelar;
	private JPanel panelNombre;
	private JLabel lblNombre;
	private IGaleria galeria;
	private IVentanaGaleria ventanaGaleria;
	
	public AnadirComic(IGaleria g) {
		galeria = g;
		
		setTitle("Añadir comic");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		panelNombre = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNombre.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setVgap(10);
		getContentPane().add(panelNombre);
		
		lblNombre = new JLabel("Nombre: ");
		panelNombre.add(lblNombre);
		
		tNombre = new JTextField();
		panelNombre.add(tNombre);
		tNombre.setColumns(10);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BorderLayout(0, 0));
		getContentPane().add(panelBotones);
		
		btnCancelar = new JButton("Cancelar");
		btnAnadir = new JButton("Añadir");
		
		panelBotones.add(btnAnadir, BorderLayout.EAST);
		
		panelBotones.add(btnCancelar, BorderLayout.WEST);
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirComic.this.cancelar();
			}
		});
	
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirComic.this.anadirVineta();
			}
		});
		
		
		this.setVisible(true);
		this.setSize(850, 600);
		this.pack();
		
		//CENTRADO DE VENTANA
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
	
	private void cerrarVentana(){
		dispatchEvent( new WindowEvent(this, WindowEvent.WINDOW_CLOSING) );
	}
	
	private void cancelar() {
		cerrarVentana();
	}
	
	private void anadirVineta() {
		Comic nuevo;
		if(tNombre.getText().equals("")) nuevo = new Comic();
		else nuevo = new Comic(tNombre.getText());
		galeria.guardarComic(nuevo);
		ventanaGaleria.addObra(nuevo);
		
		cerrarVentana();
	}
	

}
