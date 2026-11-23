package com.hotel.modelo;

// ★ REQUISITO (Asociación): Reserva une a un Recepcionista con un Huesped.
public class Reserva {

    private String fecha;
    private String servicio;
    private Huesped huesped;
    private Recepcionista recepcionista;

    // ★ REQUISITO: el constructor asigna los cuatro atributos.
    public Reserva(String fecha, String servicio, Huesped huesped, Recepcionista recepcionista) {
        // ★ EXTRA: ninguna referencia puede ser null.
        if (huesped == null || recepcionista == null) {
            throw new IllegalArgumentException("La reserva necesita un huésped y un recepcionista.");
        }
        Validador.validarTexto(fecha, "La fecha");
        Validador.validarTexto(servicio, "El servicio");
        this.fecha = fecha;
        this.servicio = servicio;
        this.huesped = huesped;
        this.recepcionista = recepcionista;
    }

    // ★ REQUISITO: getInfo() con el formato del enunciado.
    public String getInfo() {
        return "Reserva: " + fecha + " | Servicio: " + servicio
                + " | Recepcionista: " + recepcionista.getNombre()
                + " | Huésped: " + huesped.getNombre();
    }

    // ★ REQUISITO: getters de huesped, recepcionista y fecha.
    public Huesped getHuesped() {
        return huesped;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public String getFecha() {
        return fecha;
    }
}
