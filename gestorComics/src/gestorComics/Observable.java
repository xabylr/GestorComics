package gestorComics;

import java.util.ArrayList;
import java.util.List;

import gui.Observador;

public interface Observable {

public void registrar(Observador o);
public void darbBaja(Observador o);

public boolean observadoPor(Observador o);

void notificarTodos();

}
