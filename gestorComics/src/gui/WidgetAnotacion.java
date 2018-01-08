package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import gestorComics.Anotacion;
import gestorComics.Comic;
import gestorComics.Vineta;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class WidgetAnotacion extends JPanel {
	
	private Anotacion anotacion;
	
	private JPanel panelSuperior;
	private JLabel lblTitulo;
	private JLabel lblPrivacidad;
	private boolean publico;
	
	JButton btnGuardar;
	JButton btnEditar;
	
	JTextArea txtComentario;
	Comic comic;
	Vineta vineta;
	
	//Espacio para escribir un comentario
	/**
	 * @wbp.parser.constructor
	 */
	public WidgetAnotacion(Comic c, Vineta v, boolean publicidad) { //debe recibir un panel gui y una lista de anotaciones
		comic = c;
		vineta = v;
		
		publico=publicidad;

		inicializar();
		
		txtComentario.setEditable(true);
		setBackground(Color.BLUE);
		
		lblTitulo.setText("Nuevo comentario:");

		JPanel panelInferior = new JPanel(new BorderLayout());
		add (panelInferior, BorderLayout.SOUTH);
		
		btnGuardar = new JButton("Guardar");
		panelInferior.add(btnGuardar, BorderLayout.EAST);
		
		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				vineta.setAnotacion(new Anotacion(comic, vineta, null, publico));
			}
		});
	
	}
	
	
	//Mostrar anotación ya existente
	public WidgetAnotacion(Anotacion a) {
		anotacion = a;
		
		publico = anotacion.esPublico();
		inicializar();
		
		lblTitulo.setText("Editar anotación:");
		
		txtComentario.setText(anotacion.getComentario());
		txtComentario.setEditable(true);
		
		JPanel panelInferior = new JPanel(new BorderLayout());
		add (panelInferior, BorderLayout.SOUTH);
		
		btnGuardar = new JButton("Editar");
		panelInferior.add(btnGuardar, BorderLayout.EAST);
		
		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				a.setComentario(txtComentario.getText());
			}
		});
		
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
