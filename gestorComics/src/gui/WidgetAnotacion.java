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
	private JPanel panelInferior;
	private boolean publico;
	
	JButton btnGuardar;
	JButton btnEditar;
	JButton btnBorrar;
	
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

		
		btnGuardar = new JButton("Guardar");
		panelInferior.add(btnGuardar, BorderLayout.EAST);
		
		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				anotacion = new Anotacion(comic, vineta, null, publico);
				anotacion.setComentario(txtComentario.getText());
				vineta.setAnotacion(anotacion);
				lblTitulo.setText("Comentario guardado, editar: ");
				mostrarBorrar();
			}
		});
	
	}
	
	
	//Mostrar anotación ya existente
	public WidgetAnotacion(Anotacion a) {
		anotacion = a;
		comic = a.getComic();
		vineta = a.getVineta();
		
		publico = anotacion.esPublico();
		inicializar();
		
		lblTitulo.setText("Editar anotación:");
		
		txtComentario.setText(anotacion.getComentario());
		txtComentario.setEditable(true);
		
		panelInferior = new JPanel(new BorderLayout());
		add (panelInferior, BorderLayout.SOUTH);
		
		btnEditar = new JButton("Editar");
		panelInferior.add(btnEditar, BorderLayout.EAST);
		
		btnEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				anotacion.setComentario(txtComentario.getText());
			}
		});
		
		mostrarBorrar();
		
	}
	
	
	private void mostrarBorrar() {
		btnBorrar = new JButton("Borrar");
		panelInferior.add(btnBorrar, BorderLayout.WEST);
		
		btnBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (anotacion.esPublico()) vineta.delAnotacionPublica(comic);
				else vineta.delAnotacionPrivada();
				txtComentario.setText("");
				lblTitulo.setText("Nuevo comentario:");
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
		
		panelInferior = new JPanel(new BorderLayout());
		add (panelInferior, BorderLayout.SOUTH);
		
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
