package gestorComics;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.IVisorVineta;

public class ComprobadoBorrado extends JFrame{

	private JPanel contentPane;
	private JLabel texto;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	Obra obra;
	
	public static final String ACEPTAR = "ACEPTAR";
	public static final String CANCELAR = "CANCELAR";
	
	
	/**
	 * Crea una ventana para confirmar el borrado de una Obra
	 */
	public ComprobadoBorrado(Obra o) {
		obra = o;
		
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
		
		controlarBotones();
	}


	public void setTexto(String s) {
		texto.setText(s);
	}
	
	public void controlarBotones() {
		
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				obra.retirar();
				dispose();
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		
	}

}
