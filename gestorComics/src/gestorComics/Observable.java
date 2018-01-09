package gestorComics;

import gui.Observador;

public interface Observable {

public void registrar(Observador o);
public void darbBaja(Observador o);

public boolean observadoPor(Observador o);

void notificarTodos();
void notificarTodosBorrado();

}
