package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gestorComics.Obra;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class CambioNombre extends JFrame {

	public static final String ACEPTAR = "ACEPTAR";
	public static final String CANCELAR = "CANCELAR";
	
	private JPanel contentPane;
	private JTextField textField;
	
	JButton btnAceptar;
	JButton btnCancelar;
	
	Obra obra;

	/**
	 * Create the frame.
	 */
	public CambioNombre(Obra o) {
		obra = o;
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 407, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(67, 43, 67, 25);
		contentPane.add(lblNombre);
		
		textField = new JTextField();
		textField.setBounds(144, 42, 171, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(38, 124, 89, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(246, 124, 89, 23);
		contentPane.add(btnCancelar);
		
		setVisible(true);
		
		escucharBotones();
		
	}
	
	public void escucharBotones() {
		
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obra.setNombre(textField.getText());
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

	public String getNombre() {
		String res = null;
		
		if(textField.getText().length() > 0) {
			res = textField.getText();
		}
		
		return res;
	}
}
