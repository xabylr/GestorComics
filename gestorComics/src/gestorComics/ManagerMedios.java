package gestorComics;

import java.util.ArrayList;
import java.util.List;

public class ManagerMedios {
	static ManagerMedios instance;
	static IBD bd;
	
	public static ManagerMedios instance() {
		if(instance == null) {
			instance = new ManagerMedios();
		}
		return instance;
	}
	
	private ManagerMedios() {
		
	}

	public List<MedioComunicacion> getMedios(){
		List<MedioComunicacion> res = new ArrayList<MedioComunicacion>();
		
		res = bd.getMedios();
		
		return res;
	}
	
	public void addMedio(MedioComunicacion m) {
		bd.addMedio(m);
	}
	
	public void setBD(IBD bd2) {
		bd = bd2;
	}
}
