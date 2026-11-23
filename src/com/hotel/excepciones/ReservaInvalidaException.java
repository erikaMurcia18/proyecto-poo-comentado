package com.hotel.excepciones;

// ★ REQUISITO (Paquete excepciones): extiende Exception.
// ★ Constructor recibe "mensaje" y lo pasa tal cual a super(...).
public class ReservaInvalidaException extends Exception {

    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
