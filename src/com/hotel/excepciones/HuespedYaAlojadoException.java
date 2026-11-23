package com.hotel.excepciones;

// ★ EXTRA (no pedida): se lanza si se intenta alojar a un huésped que ya está en una habitación.
public class HuespedYaAlojadoException extends Exception {

    public HuespedYaAlojadoException(String mensaje) {
        super(mensaje);
    }
}
