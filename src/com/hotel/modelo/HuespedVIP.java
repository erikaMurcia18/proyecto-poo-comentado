package com.hotel.modelo;

// ★ REQUISITO: HuespedVIP declarada FINAL (no puede heredarse).
public final class HuespedVIP extends Huesped {

    private int noches;
    private int descuento; // porcentaje

    public HuespedVIP(String nombre, String documento, int edad, int noches, int descuento) {
        super(nombre, documento, edad);
        // ★ REQUISITO: validación de noches (> 0).
        if (noches <= 0) {
            throw new IllegalArgumentException("Las noches deben ser mayores a 0.");
        }
        // ★ EXTRA: límite superior de noches.
        if (noches > 365) {
            throw new IllegalArgumentException("Las noches no pueden superar 365.");
        }
        // ★ REQUISITO: validación de descuento (0 a 50).
        if (descuento < 0 || descuento > 50) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 50.");
        }
        this.noches = noches;
        this.descuento = descuento;
    }

    // ★ REQUISITO (POLIMORFISMO DINÁMICO - SOBREESCRITURA) de obtenerInfo().
    @Override
    public String obtenerInfo() {
        return "Huésped VIP: " + getNombre() + " | Documento: " + getDocumento()
                + " | Edad: " + getEdad() + " | Noches: " + noches + " | Descuento: " + descuento + "%";
    }

    // ★ REQUISITO: getDescuento().
    public int getDescuento() {
        return descuento;
    }

    public int getNoches() {
        return noches;
    }
}
