package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import gestorComics.Anotacion;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.color.ColorSpace;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class WidgetAnotacion extends JPanel {
	
	private Anotacion anotacion;
	
	private JPanel panelSuperior;
	private JLabel lblTitulo;
	private JLabel lblPrivacidad;
	private boolean publico;
	
	JTextArea txtComentario;
	
	//Espacio para escribir un comentario
	/**
	 * @wbp.parser.constructor
	 */
	public WidgetAnotacion(boolean publicidad) { //debe recibir un panel gui y una lista de anotaciones
		publico=publicidad;

		inicializar();
		
		txtComentario.setEditable(true);
		setBackground(Color.BLUE);
		
		lblTitulo.setText("Nuevo comentario:");

		JPanel panelInferior = new JPanel(new BorderLayout());
		add (panelInferior, BorderLayout.SOUTH);
		
		JButton btnAnadir = new JButton("Guardar");
		panelInferior.add(btnAnadir, BorderLayout.EAST);
		
	
	}
	
	
	//Mostrar anotación ya existente
	public WidgetAnotacion(Anotacion a) {
		anotacion = a;
		
		publico = anotacion.esPublico();
		inicializar();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/LLLL/yyyy (HH:mm)");
		
		lblTitulo.setText(anotacion.getFecha().format(dtf));
		
		txtComentario.setText(anotacion.getComentario());
		txtComentario.setEditable(false);
		
	}
	
	
	private void inicializar() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setPreferredSize(new Dimension(400, 150));
		setLayout(new BorderLayout(0, 0));
		
		panelSuperior = new JPanel();
		add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		
		lblTitulo = new JLabel("Título");
		panelSuperior.add(lblTitulo, BorderLayout.WEST);
		
		lblPrivacidad = new JLabel("¿?");
		
		lblPrivacidad.setHorizontalTextPosition(SwingConstants.LEFT);
		panelSuperior.add(lblPrivacidad, BorderLayout.EAST);
		
		txtComentario = new JTextArea();
		JScrollPane jsp = new JScrollPane(txtComentario);
		
		add(jsp, BorderLayout.CENTER);
		
		refrescarIcono();
		
	}
	
	
	private void refrescarIcono() {
		if (publico) {
			lblPrivacidad.setIcon(new ImageIcon("src/res/unlock.png"));
			lblPrivacidad.setText("Publico");
		}else {
			lblPrivacidad.setIcon(new ImageIcon("src/res/lock.png"));
			lblPrivacidad.setText("Privado");
		}
	}
	
	public Anotacion getAnotacion() {
		return anotacion;
	}
	
	

}
