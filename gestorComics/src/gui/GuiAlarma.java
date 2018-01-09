package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gestorComics.Alarma;

@SuppressWarnings("serial")
public class GuiAlarma extends JFrame {

	private JPanel contentPane;
	
	private JButton btnaceptar;
	private JLabel texto;
	private JLabel miniatura;
	
	static int ALT_IMG = 100;
	static int ANCH_IMG = 100;

	public GuiAlarma(Alarma a) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		

		texto = new JLabel(a.toString());
		add(texto, BorderLayout.CENTER);
		
		miniatura = new JLabel(new ImageIcon(a.getVineta().getImagen().getScaledInstance(ANCH_IMG, ALT_IMG, Image.SCALE_DEFAULT)));
		add(miniatura, BorderLayout.WEST);
		
		btnaceptar = new JButton("OK");
		add(btnaceptar, BorderLayout.SOUTH);
		
		btnaceptar.addActionListener(new ActionListener() 
        {
	         public void actionPerformed(ActionEvent ae) 
	         {
	               dispose();
	         }
        });
		
		pack();
		setVisible(true);
	}

}