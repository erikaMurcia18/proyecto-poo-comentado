package com.hotel.excepciones;

// ★ EXTRA (no pedida): se lanza al registrar un empleado cuyo id ya existe en el hotel.
public class EmpleadoDuplicadoException extends Exception {

    public EmpleadoDuplicadoException(String id) {
        super("Ya existe un empleado registrado con el id " + id + ".");
    }
}
