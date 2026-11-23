package com.hotel.excepciones;

// ★ REQUISITO (Paquete excepciones): excepción personalizada que extiende Exception.
// ★ Constructor recibe "mensaje" y lo pasa tal cual a super(...).
public class HabitacionLlenaException extends Exception {

    public HabitacionLlenaException(String mensaje) {
        super(mensaje);
    }
}
