package gestorComics;

public interface Obra extends Comparable<Obra>, Visualizable{
	
	public String getNombre();
	public void setNombre(String n);
	public int getID();

}
