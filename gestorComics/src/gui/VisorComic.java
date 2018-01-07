package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import gestorComics.Comic;
import gestorComics.Galeria;
import gestorComics.Obra;
import gestorComics.Observable;
import gestorComics.Vineta;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import controladores.CtrVentanaComic;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VisorComic extends JFrame implements IVisorComic, Observador{
	
	JButton btnCambiarNombre = new JButton("Cambiar nombre");
	JButton btnBorrar = new JButton("Borrar comic");
	PaneObras panevinetas;
	Comic comic;
	
	//https://stackoverflow.com/questions/2745265/is-listdog-a-subclass-of-listanimal-why-arent-javas-generics-implicitly-p
	//No pasa nada, no se añade ninguna obra a la lista de viñetas, solo se lee
	@SuppressWarnings("unchecked") 
	public VisorComic(Comic c) {
		comic = c;
		comic.registrar(this);
		
		setTitle(comic.getNombre());
		setBounds(100, 100, 741, 466);
		
		JPanel jpanelvinetas = new JPanel();
		
		JLabel jlabelvinetas = new JLabel("Viñetas");
		panevinetas = new  PaneObras((List<Obra>) (List<?>) comic.getVinetas());
		jpanelvinetas.add(jlabelvinetas, BorderLayout.NORTH);
		jpanelvinetas.add(panevinetas, BorderLayout.SOUTH);
		
		getContentPane().add(jpanelvinetas, BorderLayout.NORTH);
		
		JPanel jpanelbocetos = new JPanel();
		
		PaneObras panelbocetos;
		JLabel jlabelbocetos = new JLabel("Bocetos");
		panelbocetos = new  PaneObras((List<Obra>) (List<?>) comic.getBocetos());
		jpanelbocetos.add(jlabelbocetos, BorderLayout.NORTH);
		jpanelbocetos.add(panelbocetos, BorderLayout.SOUTH);
		
		getContentPane().add(jpanelbocetos, BorderLayout.CENTER);
		
		
		
		
		JPanel panelCerrar = new JPanel();
		getContentPane().add(panelCerrar, BorderLayout.SOUTH);
		
		JButton btnNewCerrar = new JButton("Cerrar");
		btnNewCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispatchEvent( new WindowEvent(VisorComic.this, WindowEvent.WINDOW_CLOSING) );
				
			}
		});
		
		panelCerrar.add(btnCambiarNombre);
		panelCerrar.add(btnBorrar);
		
		panelCerrar.add(btnNewCerrar);
		
		CtrVentanaComic ctr = new CtrVentanaComic(this);
		this.controlador(ctr);

		pack();
		
		setVisible(true);

	}

	@Override
	public void controlador(ActionListener ctr) {
		btnCambiarNombre.setActionCommand(IVisorComic.CAMBIARNOMBRE);
		btnCambiarNombre.addActionListener(ctr);
		btnBorrar.setActionCommand(IVisorComic.BORRARCOMIC);
		btnBorrar.addActionListener(ctr);
		
		
	}
	

	public Comic getComic() {
		return comic;
	}

	@Override
	public void setNombre(String name) {
		this.setTitle(name);
		
	}

	@Override
	public void SetImagen(Image img) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificar() {
		panevinetas.refrescar();
		
	}

	@Override
	public void notificarBorrado(Observable o) {
		dispose();
	}
	
}
