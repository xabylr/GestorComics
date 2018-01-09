package controladores;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gestorComics.IGaleria;
import gestorComics.ManagerMedios;
import gestorComics.MedioComunicacion;
import gui.*;


public class CtrVentanaGaleria implements ActionListener{
	IVentanaGaleria ventana;
	IGaleria galeria;

	public CtrVentanaGaleria(IGaleria g, IVentanaGaleria v) {
		galeria = g;
		ventana = v;
		
		galeria.conectarGUI(v);
		//al cambiar la colección se refrescará la vista
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
		switch(e.getActionCommand()) {
		case IVentanaGaleria.ADD_COMIC:
			anadirComic();
			break;
			
		case IVentanaGaleria.ADD_VINETA:
			anadirVineta();
			break;
			
		case IVentanaGaleria.ALARMA:
			anadirAlarma();
			break;
			
		case IVentanaGaleria.MEDIO:
			
			JFrame frame = new JFrame();
			JPanel contentPane;
			JTextField textField;
			
			JButton btnAceptar;
			JButton btnCancelar;
			
			frame.setBounds(102, 102, 409, 199);
			contentPane = new JPanel();
			contentPane.setBounds(100, 100, 407, 197);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
			
			frame.add(contentPane);
			
			btnAceptar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ManagerMedios.instance().addMedio(new MedioComunicacion(textField.getText()));
					//galeria.anadirMedio();
					frame.dispose();
				}
			});
			
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			
			frame.setVisible(true);
		}

	}
	
	
	private void anadirComic() {
		System.out.println("Añadir Comic");
		new AnadirComic(galeria);

	}
	
	private void anadirVineta() {
		System.out.println("Añadir Viñeta");
		new AnadirVineta(galeria);
	}
	
	private void anadirAlarma() {
		System.out.println("Añadir alarma"); //DONDE SE CREA EL MANAGER
		AnadirAlarma alarma = new AnadirAlarma(galeria);
		CtrAnadirAlarma ctr = new CtrAnadirAlarma(alarma);
		alarma.controlador(ctr);
	}



	
}
