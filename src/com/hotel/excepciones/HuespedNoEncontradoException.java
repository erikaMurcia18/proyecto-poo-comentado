package com.hotel.excepciones;

// ★ REQUISITO (Paquete excepciones): extiende Exception.
// ★ Constructor recibe "documento" y arma el mensaje exigido por el enunciado.
public class HuespedNoEncontradoException extends Exception {

    public HuespedNoEncontradoException(String documento) {
        super("Huésped con documento " + documento + " no encontrado.");
    }
}
