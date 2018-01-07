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
import gui.AnadirAlarma;
import gui.IAnadirAlarma;

public class CtrAnadirAlarma implements ActionListener {
	
	AnadirAlarma alarmaVentana;
	
	public CtrAnadirAlarma(AnadirAlarma a) {
		alarmaVentana = a;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String str = arg0.getActionCommand();
		
		if(str.equals(IAnadirAlarma.FECHA)) {

			final JFrame f = new JFrame();
			String datePicked = new DatePicker(f).setPickedDate();
			alarmaVentana.txtFechaAqui.setText(datePicked);
			Calendar calendario = Calendar.getInstance();
			int horas = calendario.get(Calendar.HOUR_OF_DAY);
			int minutos = calendario.get(Calendar.MINUTE);
			
			
			try(Scanner sc = new Scanner(datePicked)){
				sc.useDelimiter("-");
				alarmaVentana.fecha = new Date(Integer.parseInt(sc.next())-1900,Integer.parseInt(sc.next()),Integer.parseInt(sc.next()), horas, minutos);
			}
		} else if(str.equals(IAnadirAlarma.ANADIR)) {
//			if(alarmaVentana.getComic() != null && alarmaVentana.getVineta() != null && alarmaVentana.getFecha() != null && alarmaVentana.getMedio() != null) {
				System.out.println("Creamos alarma");
				Alarma alarma = new Alarma (-1,alarmaVentana.getComic(),alarmaVentana.getVineta(),alarmaVentana.getMedio(), alarmaVentana.getFecha());
				alarmaVentana.getManager().addAlarma(alarma);
				
//			}
		}
		
	}

}
