package com.hotel.excepciones;

// ★ EXTRA (no pedida): se lanza al registrar un huésped cuyo documento ya está registrado.
public class HuespedDuplicadoException extends Exception {

    public HuespedDuplicadoException(String documento) {
        super("Ya existe un huésped registrado con el documento " + documento + ".");
    }
}
