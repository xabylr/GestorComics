package gestorComics;

public interface Sincronizable {
	
	public int getID();
	
	public void setID(int id);
	
	public void conectar(IBD b);
	
	public void subir();
	
	public void retirar(); //Borramos de la BD
	
}
