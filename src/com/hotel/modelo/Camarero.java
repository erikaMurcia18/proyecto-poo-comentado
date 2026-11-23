package com.hotel.modelo;

// ★ REQUISITO (Herencia): Camarero extiende Empleado.
public class Camarero extends Empleado {

    // ★ REQUISITO: atributo private zona.
    private String zona;

    // ★ REQUISITO: constructor llama super(nombre, id, cargo) y asigna zona.
    public Camarero(String nombre, String id, String cargo, String zona) {
        super(nombre, id, cargo);
        // ★ EXTRA: la zona no puede estar vacía.
        Validador.validarTexto(zona, "La zona");
        this.zona = zona.trim();
    }

    // ★ REQUISITO (POLIMORFISMO DINÁMICO - SOBREESCRITURA) de generarReporte().
    @Override
    public String generarReporte() {
        return "=== CAMARERO ===\nNombre: " + getNombre() + "\nID: " + getId()
                + "\nCargo: " + getCargo() + "\nZona: " + zona;
    }

    public String getZona() {
        return zona;
    }
}
