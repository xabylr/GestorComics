package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import gestorComics.Comic;
import gestorComics.Galeria;
import gestorComics.Obra;
import gestorComics.Vineta;

import javax.swing.JPanel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VisorComic extends JFrame implements IVisorComic{
	
	//https://stackoverflow.com/questions/2745265/is-listdog-a-subclass-of-listanimal-why-arent-javas-generics-implicitly-p
	//No pasa nada, no se añade ninguna obra a la lista de viñetas, solo se lee
	@SuppressWarnings("unchecked") 
	public VisorComic(Comic comic) {
		setTitle(comic.getNombre());
		setBounds(100, 100, 741, 466);
		
		PaneObras po;
		po = new  PaneObras((List<Obra>) (List<?>) comic.getVinetas());
		add(po, BorderLayout.CENTER);
		
		
		
		
		JPanel panelCerrar = new JPanel();
		add(panelCerrar, BorderLayout.SOUTH);
		
		JButton btnNewCerrar = new JButton("Cerrar");
		btnNewCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispatchEvent( new WindowEvent(VisorComic.this, WindowEvent.WINDOW_CLOSING) );
				
			}
		});
		
		panelCerrar.add(btnNewCerrar);
		
		setVisible(true);
	}
	
	

}
