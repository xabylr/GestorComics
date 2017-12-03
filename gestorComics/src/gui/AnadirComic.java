package gui;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import gestorComics.Comic;
import gestorComics.Galeria;

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
public class AnadirComic extends JFrame implements IAnadirComic {
	
	private JTextField tNombre;
	JButton btnAnadir;
	JButton btnCancelar;
	private JPanel panelNombre;
	private JLabel lblNombre;
	
	public AnadirComic() {
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
				dispatchEvent( new WindowEvent(AnadirComic.this, WindowEvent.WINDOW_CLOSING) );
			}
		});
	
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Comic nuevo;
				if(tNombre.getText().equals("")) nuevo = new Comic();
				else nuevo = new Comic(tNombre.getText());
				
				Galeria.getGaleria().insert(nuevo);
				VentanaGaleria.getVentana().addObra(nuevo);
				dispatchEvent( new WindowEvent(AnadirComic.this, WindowEvent.WINDOW_CLOSING) );
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



	@Override
	public String devuelveTexto() {
		return tNombre.getText();
		
	}
}
