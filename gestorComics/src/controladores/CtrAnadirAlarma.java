package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import gestorComics.Alarma;
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
			UtilDateModel model = new UtilDateModel();
			JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
			Date selectedDate = (Date) datePicker.getModel().getValue();
			alarmaVentana.setFecha(selectedDate);
		} else if(str.equals(IAnadirAlarma.ANADIR)) {
			if(alarmaVentana.getComic() != null && alarmaVentana.getVineta() != null && alarmaVentana.getFecha() != null) {
				//HAY QUE VER COMO VA EL CONTADOR y MEDIOS
				Alarma alarma = new Alarma (-1,alarmaVentana.getComic(),alarmaVentana.getVineta(),null, alarmaVentana.getFecha());
				alarmaVentana.getManager().addAlarma(alarma);
				System.out.println(alarma.getFecha());
			}
		}
		
	}

}
