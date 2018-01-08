

package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gestorComics.Anotacion;
import gestorComics.Comic;
import gestorComics.Vineta;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;

import excepciones.RecursoNoEncontrado;

@SuppressWarnings("serial")
public class VentanaVineta extends JFrame implements IVisorVineta {
	
	private static final int ALT_IMG = 500;
	private static final int ANCH_IMG = 500;
	private JButton btnCerrar;
	JLabel limagen;
	
	private JButton btnBorrar;
	private JButton btnCambiarNombre;
	private JButton btnCambiarImagen;
	
	Vineta vineta;
	Comic comic;
	
	PaneAnotaciones paneAnotaciones;
	
	public VentanaVineta(Vineta v, Comic c) throws RecursoNoEncontrado {

		
		if(v==null || v.getImagen() ==null)
			throw new RecursoNoEncontrado("No hay nada que mostrar");

		
		vineta = v;
		comic = c;
		
		btnCerrar = new JButton("Cerrar");
		
		btnBorrar = new JButton("Borrar");
		
		btnCambiarImagen = new JButton("Cambiar Imagen");
		
		btnCambiarNombre = new JButton("Cambiar Nombre");
		
		JPanel panelVisor = new JPanel();
		/*GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(165)
							.addComponent(btnCerrar))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addComponent(panelVisor, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(panelVisor, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnCerrar)
					.addGap(19))
		);*/
		
		limagen = new JLabel("");
		panelVisor.add(limagen);
		//getContentPane().setLayout(groupLayout);
		
		setTitle(v.getNombre());
		limagen.setIcon(new ImageIcon(v.getImagen().getScaledInstance(ANCH_IMG, ALT_IMG, Image.SCALE_DEFAULT)));
		getContentPane().add(panelVisor, BorderLayout.WEST);
		//pack();
		
		//CENTRADO DE VENTANA
	    /*Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);*/
	    
		
		//Panel de anotaciones
		paneAnotaciones = new PaneAnotaciones(comic, vineta);
		
		Anotacion aprivada = vineta.getAnotacionPrivada();
		Anotacion apublica = vineta.getAnotacionPublica(comic);
		
		if(aprivada!=null)
			paneAnotaciones.setAnotacion(aprivada);
		if(apublica !=null)
		paneAnotaciones.setAnotacion(apublica);
		
		getContentPane().add(paneAnotaciones, BorderLayout.EAST);
		
		
	    
	    JPanel panelBotones = new JPanel();
	    panelBotones.add(btnCerrar);
	    panelBotones.add(btnBorrar);
	    panelBotones.add(btnCambiarNombre);
	    panelBotones.add(btnCambiarImagen);
	    
	    add(panelBotones, BorderLayout.SOUTH);
	    
	    pack();
		setVisible(true);
	}

	@Override
	public void controlador(ActionListener ctr) {
		btnCerrar.setActionCommand(IVisorVineta.CANCELAR);
		btnCerrar.addActionListener(ctr);
		
		btnBorrar.setActionCommand(IVisorVineta.BORRAR);
		btnBorrar.addActionListener(ctr);
		
		btnCambiarNombre.setActionCommand(IVisorVineta.CAMBIARNOMBRE);
		btnCambiarNombre.addActionListener(ctr);
		
		btnCambiarImagen.setActionCommand(IVisorVineta.CAMBIARIMAGEN);
		btnCambiarImagen.addActionListener(ctr);
		
		
	}
	
	public void SetImagen(Image img) {
		limagen.setIcon(new ImageIcon(img.getScaledInstance(ANCH_IMG, ALT_IMG, Image.SCALE_DEFAULT)));
	}
	
	public void SetNombre(String name) {
		setTitle(name);
	}
	
	

	@Override
	public Vineta getVineta() {
		
		return vineta;
	}
	
}