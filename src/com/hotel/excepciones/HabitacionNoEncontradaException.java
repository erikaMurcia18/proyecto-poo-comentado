package com.hotel.excepciones;

// ★ EXTRA (no pedida): se lanza cuando el usuario escribe un número de habitación que no existe.
public class HabitacionNoEncontradaException extends Exception {

    public HabitacionNoEncontradaException(String numero) {
        super("La habitación '" + numero + "' no existe.");
    }
}
