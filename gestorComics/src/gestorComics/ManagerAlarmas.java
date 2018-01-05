package gestorComics;

import java.util.List;

public class ManagerAlarmas {
	
	static ManagerAlarmas instance;
	
	public static ManagerAlarmas instance() {
		
		if(instance == null) {
			instance = new ManagerAlarmas();
		}
		
		return instance;
	}
	
	private ManagerAlarmas() {
		
	}
	
	public void addAlarma(Alarma a) {
		
		
		a.start();
	}
	
	public void addAlarma(List<Alarma> list) {
		
		for(Alarma a : list) {
			
			a.start();
		}
		
		
	}
	
	public void removeAlarma(Alarma a) {
		
	}
}
