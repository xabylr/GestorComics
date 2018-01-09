package gestorComics;

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
		
		//TODO arreglar base de datos
	//	if(bd!=null)
		//a.setIdentificador(bd.addAlarma(a));
		startAlarma(a);
	}
	
	public void startAlarma(Alarma a) {
			
			a.start();		
	}
	
	public void activarAlarma(Alarma a) {
		
		new GuiAlarma(a);
		
		if(bd!=null)
		bd.removeAlarma(a);
	}
}