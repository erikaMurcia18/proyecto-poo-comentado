package com.hotel.excepciones;

// ★ REQUISITO (Paquete excepciones): extiende Exception.
// ★ Constructor recibe "id" y arma el mensaje exigido por el enunciado.
public class EmpleadoNoEncontradoException extends Exception {

    public EmpleadoNoEncontradoException(String id) {
        super("No existe empleado registrado con id " + id + ".");
    }
}
