package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gestorComics.Vineta;

import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;

import excepciones.RecursoNoEncontrado;

@SuppressWarnings("serial")
public class VisorVineta extends JFrame implements IVisorVineta {
	
	public JButton btnCerrar;
	JLabel limagen;
	
	public VisorVineta(Vineta vineta) throws RecursoNoEncontrado {
		
		if(vineta==null || vineta.getImagen() ==null)
			throw new RecursoNoEncontrado("No hay nada que mostrar");

		
		btnCerrar = new JButton("Cerrar");
		
		JPanel panelVisor = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
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
		);
		
		BufferedImage im = (BufferedImage) vineta.getImagen();
		limagen = new JLabel(new ImageIcon(im.getScaledInstance(im.getWidth(), im.getHeight(), Image.SCALE_DEFAULT)));
		panelVisor.add(limagen);
		setTitle(vineta.getNombre());
		limagen.setIcon(new ImageIcon(im.getScaledInstance(im.getWidth(), im.getHeight(), Image.SCALE_DEFAULT)) );

		pack();
		
		//CENTRADO DE VENTANA
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	    
		setVisible(true);
	}

	@Override
	public void controlador(ActionListener ctr) {
		btnCerrar.setActionCommand(IVisorVineta.CANCELAR);
		btnCerrar.addActionListener(ctr);
		
		
	}
	
	public void SetImagen(Image img) {
		
	}
	
	public void SetNombre(String name) {
		setTitle(name);
	}
}
