package gestorComics;

public interface Sincronizable {
	
	public int getID();
	
	public void setID(int id);
	
	public void conectar(IBD b);
	
	public void guardar();
	
	public void desConectar(); //Borramos de la BD
	
}
