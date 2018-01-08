package gestorComics;

import java.util.List;

import gui.GuiAlarma;

public class ManagerAlarmas {
	
	static ManagerAlarmas instance;
	static IBD bd;
	
	public static ManagerAlarmas instance() {
		
		if(instance == null) {
			instance = new ManagerAlarmas();
		}
		
		return instance;
	}
	
	private ManagerAlarmas() {
		
	}
	
	public void setBD(IBD db) {
		bd = db;
		
		for(Alarma a : bd.getAlarmas()) {
			
			startAlarma(a);
		}
	}
	
	public void addAlarma(Alarma a) {
		
		a.setIdentificador(bd.addAlarma(a));
		startAlarma(a);
	}
	
	public void startAlarma(Alarma a) {
			
			a.start();		
	}
	
	public void activarAlarma(Alarma a) {
		
		GuiAlarma guialarma = new GuiAlarma(a);
		
		bd.removeAlarma(a);
	}
}