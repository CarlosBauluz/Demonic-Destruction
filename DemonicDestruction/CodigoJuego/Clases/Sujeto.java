package Clases;

import java.util.ArrayList;
import java.util.List;

public interface Sujeto {
    void agregarObservador(Observador observador);
    void eliminarObservador(Observador observador);
    void notificarObservadores();
}