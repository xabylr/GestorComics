package gestorComics;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.IVisorVineta;

public class ComprobadoBorrado extends JFrame implements IComprobadoBorrado{

	private JPanel contentPane;
	private JLabel texto;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	

	/**
	 * Create the frame.
	 */
	public ComprobadoBorrado() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		texto = new JLabel();
		contentPane.add(texto);
		
		//add(contentPane, BorderLayout.CENTER);
		
		JPanel pbotones = new JPanel();
		
		btnAceptar = new JButton("Aceptar");
		pbotones.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		pbotones.add(btnCancelar);
		
		add(pbotones, BorderLayout.SOUTH);
		
		setVisible(true);
	}


	public void setTexto(String s) {
		texto.setText(s);
	}
	
	@Override
	public void controlador(ActionListener ctr) {
		
		btnAceptar.setActionCommand(IComprobadoBorrado.ACEPTAR);
		btnAceptar.addActionListener(ctr);
		
		btnCancelar.setActionCommand(IComprobadoBorrado.CANCELAR);
		btnCancelar.addActionListener(ctr);
		
	}

}
