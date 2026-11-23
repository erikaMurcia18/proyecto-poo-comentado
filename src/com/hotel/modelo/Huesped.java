package com.hotel.modelo;

// ★ REQUISITO (Jerarquía de herencia): clase ABSTRACTA Huesped.
public abstract class Huesped {

    // ★ REQUISITO (Encapsulamiento): atributos private.
    private String nombre;
    private String documento;
    private int edad;

    public Huesped(String nombre, String documento, int edad) {
        // ★ REQUISITO: validación de nombre (sin dígitos) y documento (solo dígitos, 5 a 12) vía Validador.
        Validador.validarNombre(nombre);
        Validador.validarDocumento(documento);
        // ★ REQUISITO: validación de edad (0 a 120).
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("Edad inválida: " + edad);
        }
        this.nombre = nombre.trim();
        this.documento = documento;
        this.edad = edad;
    }

    // ★ REQUISITO: getters de nombre, documento y edad.
    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public int getEdad() {
        return edad;
    }

    // ★ REQUISITO: obtenerInfo() ABSTRACTO (polimorfismo dinámico).
    public abstract String obtenerInfo();
}
