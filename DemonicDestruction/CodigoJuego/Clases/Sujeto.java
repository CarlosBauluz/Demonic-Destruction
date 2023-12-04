package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface Sujeto.
 */
public interface Sujeto {
    /**
     * Agregar observador.
     *
     * @param observador the observador
     */
    void agregarObservador(Observador observador);

    /**
     * Eliminar observador.
     *
     * @param observador the observador
     */
    void eliminarObservador(Observador observador);

    /**
     * Notificar observadores.
     */
    void notificarObservadores();
}