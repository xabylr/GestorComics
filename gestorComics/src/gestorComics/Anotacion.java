package gestorComics;

public class Anotacion implements Sincronizable {

	protected String comentario;
	
	private Comic comic;
	private Vineta vineta;
	private Vineta boceto;
	private IBD bd;
	
	private boolean publico;
	
	public Anotacion(Comic c, Vineta v, Vineta b,  boolean p) {
		comic = c; vineta=v; boceto = b; publico = p;
	}
	
	
	public  boolean esPublico() {
		return publico;
	}
	
	public String getComentario(){
		return comentario;
	}
	
	public Comic getComic() {
		return comic;
	}
	
	public Vineta getVineta() {
		return vineta;
	}
	
	public Vineta getBoceto() {
		return boceto;
	}
	
	public void setComentario(String c) {
		comentario = c;
		
		if(bd!=null)bd.insertarAnotacion(comic, vineta, boceto, c);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conectar(IBD b) {
		bd = b;
	}

	@Override
	public void subir() {
		bd.insertarAnotacion(comic, vineta, boceto, comentario);
		
	}

	@Override
	public void retirar() {
		bd.borrarAnotacion(comic, vineta, boceto);
		
	}
	
	
}
