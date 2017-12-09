package gestorComics;

import java.util.List;

public interface Anotable {

	public void setAnotacionPublica(AnotacionPublica a);
	public AnotacionPublica getAnotacionPublica();
	public void delAnotacionPublica();
	
	public void addAnotacionPrivada(AnotacionPrivada a);
	public List<AnotacionPrivada> getAnotacionesPrivadas();
	public void delAnotacionPrivada(AnotacionPrivada a);
	
}
