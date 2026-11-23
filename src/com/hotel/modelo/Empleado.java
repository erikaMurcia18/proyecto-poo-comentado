package com.hotel.modelo;

// ★ REQUISITO (Jerarquía de herencia): clase ABSTRACTA Empleado.
public abstract class Empleado {

    // ★ REQUISITO (Encapsulamiento): todos los atributos son private.
    private String nombre;
    private String id;
    private String cargo;

    // ★ REQUISITO: Constructor llama Validador.validarNombre(nombre) y asigna nombre, id, cargo.
    public Empleado(String nombre, String id, String cargo) {
        Validador.validarNombre(nombre);
        // ★ EXTRA: id y cargo no pueden estar vacíos.
        Validador.validarTexto(id, "El id");
        Validador.validarTexto(cargo, "El cargo");
        this.nombre = nombre.trim();
        this.id = id.trim();
        this.cargo = cargo.trim();
    }

    // ★ REQUISITO: getters de los tres atributos (las subclases usan estos getters).
    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    // ★ REQUISITO: generarReporte() ABSTRACTO (polimorfismo dinámico: cada subclase lo sobreescribe).
    public abstract String generarReporte();
}
