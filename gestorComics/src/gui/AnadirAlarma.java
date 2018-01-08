package gui;

import javax.swing.JFrame;

import gestorComics.Alarma;
import gestorComics.Comic;
import gestorComics.IComprobadoBorrado;
import gestorComics.IGaleria;
import gestorComics.ManagerAlarmas;
import gestorComics.MedioComunicacion;
import gestorComics.Vineta;

import java.awt.BorderLayout;
import javax.swing.JLabel;

import controladores.CtrAnadirAlarma;
import controladores.CtrVentanaComic;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;

public class AnadirAlarma extends JFrame implements IAnadirAlarma {
	
	ManagerAlarmas manager;
	public Date fecha;
	private MedioComunicacion medio;
	JButton btnAceptar;
	JComboBox<Comics> comboBox;
	JComboBox<Vinetas> comboBox_1;
	JButton btnEscogerFecha;
	IGaleria galeria;
	private JLabel lblMedio;
	private JComboBox<Medios> comboBox_2;
	public JTextField txtFechaAqui;
	private JSpinner spinner;
	private JSpinner spinner_1;
	private JLabel lblHoras;
	private JLabel lblMinutos;
	
	public AnadirAlarma(IGaleria g) {
		galeria = g;
		manager = galeria.getManager();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblCmic = new JLabel("Cómic:");
		GridBagConstraints gbc_lblCmic = new GridBagConstraints();
		gbc_lblCmic.insets = new Insets(0, 0, 5, 5);
		gbc_lblCmic.gridx = 3;
		gbc_lblCmic.gridy = 1;
		getContentPane().add(lblCmic, gbc_lblCmic);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 4;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBox, gbc_comboBox);
		
		List<Comic> lc = galeria.getComics();
		for(Comic c : lc) {
			addComic(c);
		}
		
		JLabel lblVieta = new JLabel("Viñeta:");
		GridBagConstraints gbc_lblVieta = new GridBagConstraints();
		gbc_lblVieta.insets = new Insets(0, 0, 5, 5);
		gbc_lblVieta.gridx = 3;
		gbc_lblVieta.gridy = 3;
		getContentPane().add(lblVieta, gbc_lblVieta);
		
		comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 4;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 4;
		gbc_comboBox_1.gridy = 3;
		getContentPane().add(comboBox_1, gbc_comboBox_1);
		
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				comboBox_1.removeAllItems();
				List<Vineta> lv = getComic().getVinetas();
				for(Vineta c : lv) {
					addVineta(c);
				}
			}
			
		});
		
		lblMedio = new JLabel("Medio");
		GridBagConstraints gbc_lblMedio = new GridBagConstraints();
		gbc_lblMedio.insets = new Insets(0, 0, 5, 5);
		gbc_lblMedio.gridx = 3;
		gbc_lblMedio.gridy = 5;
		getContentPane().add(lblMedio, gbc_lblMedio);
		
		comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.gridwidth = 4;
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 4;
		gbc_comboBox_2.gridy = 5;
		getContentPane().add(comboBox_2, gbc_comboBox_2);
		
		List<MedioComunicacion> lm = galeria.getMedios();
		for(MedioComunicacion m : lm) {
			addMedio(m);
		}
		
		
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 24, 1); //default value,lower bound,upper bound,increment by
		
		txtFechaAqui = new JTextField();
		txtFechaAqui.setText("FECHA AQUI");
		GridBagConstraints gbc_txtFechaAqui = new GridBagConstraints();
		gbc_txtFechaAqui.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaAqui.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaAqui.gridx = 2;
		gbc_txtFechaAqui.gridy = 7;
		getContentPane().add(txtFechaAqui, gbc_txtFechaAqui);
		txtFechaAqui.setColumns(10);
		
		SpinnerModel sm1 = new SpinnerNumberModel(0, 0, 59, 1); //default value,lower bound,upper bound,increment by
		spinner = new JSpinner(sm);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 3;
		gbc_spinner.gridy = 7;
		getContentPane().add(spinner, gbc_spinner);
		
		lblHoras = new JLabel("Horas");
		GridBagConstraints gbc_lblHoras = new GridBagConstraints();
		gbc_lblHoras.insets = new Insets(0, 0, 5, 5);
		gbc_lblHoras.gridx = 4;
		gbc_lblHoras.gridy = 7;
		getContentPane().add(lblHoras, gbc_lblHoras);
		spinner_1 = new JSpinner(sm1);
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 6;
		gbc_spinner_1.gridy = 7;
		getContentPane().add(spinner_1, gbc_spinner_1);
		
		lblMinutos = new JLabel("Minutos");
		GridBagConstraints gbc_lblMinutos = new GridBagConstraints();
		gbc_lblMinutos.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinutos.gridx = 7;
		gbc_lblMinutos.gridy = 7;
		getContentPane().add(lblMinutos, gbc_lblMinutos);
		
		btnEscogerFecha = new JButton("Escoger fecha");
		GridBagConstraints gbc_btnEscogerFecha = new GridBagConstraints();
		gbc_btnEscogerFecha.insets = new Insets(0, 0, 0, 5);
		gbc_btnEscogerFecha.gridx = 2;
		gbc_btnEscogerFecha.gridy = 8;
		getContentPane().add(btnEscogerFecha, gbc_btnEscogerFecha);
		
		btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.gridx = 8;
		gbc_btnAceptar.gridy = 8;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		this.pack();
		this.setVisible(true);
	}

	public void controlador(ActionListener ctr) {
		btnAceptar.setActionCommand(IAnadirAlarma.ANADIR);
		btnAceptar.addActionListener(ctr);
		btnEscogerFecha.setActionCommand(IAnadirAlarma.FECHA);
		btnEscogerFecha.addActionListener(ctr);
	}
	
	public ManagerAlarmas getManager() {
		return manager;
	}
	
	@Override
	public void addComic(Comic c) {
		comboBox.addItem(new Comics(c));
	}

	@Override
	public Comic getComic() {
		if(comboBox.getSelectedItem()!=null)
			return ( (Comics) comboBox.getSelectedItem() ).getComic();
		else return null;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public MedioComunicacion getMedio() {
		if(comboBox_2.getSelectedItem()!=null)
			return ( (Medios) comboBox_2.getSelectedItem() ).getMedio();
		else return null;
	}



public void addVineta(Vineta v) {
	comboBox_1.addItem(new Vinetas(v));
}

public void addMedio(MedioComunicacion m) {
	comboBox_2.addItem(new Medios(m));
}

public Vineta getVineta() {
	if(comboBox_1.getSelectedItem()!=null)
		return ( (Vinetas) comboBox_1.getSelectedItem() ).getVineta();
	else return null;
}



public void setFecha(Date f) {
	fecha = f;
}

public int getHora() {
	return (int) spinner.getValue();
}

public int getMinutos() {
	return (int) spinner_1.getValue();
}

}

class Vinetas{
	Vineta vineta;
	public Vinetas(Vineta v) {
		vineta = v;
	}
	@Override
	public String toString() {
		return vineta.getNombre();
	}
	public Vineta getVineta() {return vineta;
	
	}
	
}

class Comics{
	Comic comic;
	public Comics(Comic c) {
		comic = c;
	}
	@Override
	public String toString() {
		return comic.getNombre();
	}
	public Comic getComic() {return comic;}
	
}

class Medios{
	MedioComunicacion medio;
	public Medios(MedioComunicacion m) {
		medio = m;
	}
	@Override
	public String toString() {
		return medio.getNombre();
	}
	public MedioComunicacion getMedio() {return medio;}
	
}