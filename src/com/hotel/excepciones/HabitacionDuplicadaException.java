package com.hotel.excepciones;

// ★ EXTRA (no pedida): se lanza al crear una habitación con un número que ya existe.
public class HabitacionDuplicadaException extends Exception {

    public HabitacionDuplicadaException(String numero) {
        super("Ya existe una habitación con el número '" + numero + "'.");
    }
}
