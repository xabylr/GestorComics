package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import gestorComics.Alarma;
import gestorComics.DatePicker;
import gestorComics.ManagerAlarmas;
import gui.AnadirAlarma;
import gui.IAnadirAlarma;

public class CtrAnadirAlarma implements ActionListener {
	
	AnadirAlarma alarmaVentana;
	
	int ano;
	int mes;
	int dia;
	
	public CtrAnadirAlarma(AnadirAlarma a) {
		alarmaVentana = a;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String str = arg0.getActionCommand();
		
		if(str.equals(IAnadirAlarma.FECHA)) {

			final JFrame f = new JFrame();
			String datePicked = new DatePicker(f, this).setPickedDate();
			alarmaVentana.txtFechaAqui.setText(datePicked);
			
			
			/*try(Scanner sc = new Scanner(datePicked)){
				sc.useDelimiter("-");
				alarmaVentana.fecha = new Date(Integer.parseInt(sc.next())-1900,Integer.parseInt(sc.next()),Integer.parseInt(sc.next()), alarmaVentana.getHora(), alarmaVentana.getMinutos());
			}*/
			
			
		} else if(str.equals(IAnadirAlarma.ANADIR)) {
//			if(alarmaVentana.getComic() != null && alarmaVentana.getVineta() != null && alarmaVentana.getFecha() != null && alarmaVentana.getMedio() != null) {
				
				Alarma alarma = new Alarma (alarmaVentana.getComic(),alarmaVentana.getVineta(),alarmaVentana.getMedio(), ano, mes, dia, alarmaVentana.getHora(), alarmaVentana.getMinutos());
				ManagerAlarmas.instance().addAlarma(alarma);
				System.out.println("Creamos alarma");
				alarmaVentana.dispose();
//			}
		}
		
	}
	
	public void setFecha(int a, int m, int d) {
		ano = a;
		mes = m;
		dia = d;
	}

}