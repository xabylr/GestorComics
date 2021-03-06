package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import gestorComics.Comic;
import gestorComics.Obra;
import gestorComics.Observable;
import gestorComics.Vineta;

import javax.swing.JPanel;
import controladores.CtrVentanaComic;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VentanaComic extends JFrame implements IVisorComic, Observador{
	
	JButton btnCambiarNombre = new JButton("Cambiar nombre");
	JButton btnBorrar = new JButton("Borrar comic");
	PaneObras panevinetas;
	JPanel jpanelbocetos;
	PaneObras panebocetos;
	Comic comic;
	
	//https://stackoverflow.com/questions/2745265/is-listdog-a-subclass-of-listanimal-why-arent-javas-generics-implicitly-p
	//No pasa nada, no se añade ninguna obra a la lista de viñetas, solo se lee
	@SuppressWarnings("unchecked") 
	public VentanaComic(Comic c) {
		comic = c;
		comic.registrar(this);
		
		setTitle(comic.getNombre());
		setBounds(100, 100, 741, 466);
		
		JPanel jpanelvinetas = new JPanel();
		
		JLabel jlabelvinetas = new JLabel("Viñetas");
		panevinetas = new  PaneObras((List<Obra>) (List<?>) comic.getVinetas(), comic);
		jpanelvinetas.add(jlabelvinetas, BorderLayout.NORTH);
		jpanelvinetas.add(panevinetas, BorderLayout.SOUTH);
		
		getContentPane().add(jpanelvinetas, BorderLayout.NORTH);
		
		jpanelbocetos = new JPanel();
		
		JLabel jlabelbocetos = new JLabel("Bocetos");
		panebocetos = new  PaneObras((List<Obra>) (List<?>) comic.getBocetos(), comic);
		jpanelbocetos.add(jlabelbocetos, BorderLayout.NORTH);
		jpanelbocetos.add(panebocetos, BorderLayout.SOUTH);
		
		getContentPane().add(jpanelbocetos, BorderLayout.CENTER);
		
		
		
		
		JPanel panelCerrar = new JPanel();
		getContentPane().add(panelCerrar, BorderLayout.SOUTH);
		
		JButton btnNewCerrar = new JButton("Cerrar");
		btnNewCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispatchEvent( new WindowEvent(VentanaComic.this, WindowEvent.WINDOW_CLOSING) );
				
			}
		});
		
		panelCerrar.add(btnCambiarNombre);
		
		JButton btnInsertarBoceto = new JButton("Insertar Boceto");
		btnInsertarBoceto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AnadirBoceto(comic);
			}
		});
		panelCerrar.add(btnInsertarBoceto);
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
		/*for(Vineta b : comic.getBocetos() )
			panelbocetos.addObra(obra);*/
		panebocetos.refrescar();
		repaint();
		validate();
		pack();
		
		System.out.println(comic.getBocetos() );
		
		setNombre(comic.getNombre());
		
	}

	@Override
	public void notificarBorrado(Observable o) {
		dispose();
	}
	
}
