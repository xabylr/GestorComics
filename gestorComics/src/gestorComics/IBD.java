package gestorComics;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IBD {

	public void insertarObra(Obra obra) throws IOException, SQLException;
	public List<Obra> getObras() throws SQLException;
	public List<Vineta> getVinetas(Comic comic)throws SQLException;

}
