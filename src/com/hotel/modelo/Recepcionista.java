package com.hotel.modelo;

import com.hotel.excepciones.ReservaInvalidaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

// ★ REQUISITO (Herencia): Recepcionista extiende Empleado.
public class Recepcionista extends Empleado {

    // ★ REQUISITO: atributo private reservas (ArrayList<Reserva>) inicializado vacío.
    // ★ REQUISITO (Asociación): Recepcionista -> Reserva.
    private ArrayList<Reserva> reservas;

    // ★ EXTRA: formato de fecha estricto dd/MM/aaaa.
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public Recepcionista(String nombre, String id, String cargo) {
        super(nombre, id, cargo);
        this.reservas = new ArrayList<>();
    }

    // ★ REQUISITO (POLIMORFISMO ESTÁTICO - SOBRECARGA), primera versión: hacerReserva(fecha, huesped).
    public void hacerReserva(String fecha, Huesped huesped) throws ReservaInvalidaException {
        // ★ Validaciones del enunciado (fecha vacía, huésped null) + extras, en el método auxiliar.
        validarReserva(fecha, "Alojamiento estándar", huesped);
        // ★ Crea la Reserva con servicio = "Alojamiento estándar", el huésped REAL y recepcionista = this.
        reservas.add(new Reserva(fecha.trim(), "Alojamiento estándar", huesped, this));
        System.out.println("Reserva hecha para " + fecha.trim() + " a nombre de " + huesped.getNombre());
    }

    // ★ REQUISITO (POLIMORFISMO ESTÁTICO - SOBRECARGA), segunda versión: hacerReserva(fecha, servicio, huesped).
    public void hacerReserva(String fecha, String servicio, Huesped huesped) throws ReservaInvalidaException {
        // ★ Mismas validaciones + servicio vacío (ver validarReserva).
        validarReserva(fecha, servicio, huesped);
        reservas.add(new Reserva(fecha.trim(), servicio.trim(), huesped, this));
        System.out.println("Reserva hecha para " + fecha.trim() + " con servicio " + servicio.trim()
                + " a nombre de " + huesped.getNombre());
    }

    // ★ Método auxiliar privado: concentra TODAS las validaciones de una reserva.
    private void validarReserva(String fecha, String servicio, Huesped huesped) throws ReservaInvalidaException {
        // ★ REQUISITO: fecha nula o vacía
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new ReservaInvalidaException("La fecha no puede estar vacía.");
        }
        // ★ REQUISITO: la reserva debe quedar asociada a un huésped REAL, nunca null
        if (huesped == null) {
            throw new ReservaInvalidaException("Debe indicarse un huésped válido.");
        }
        // ★ REQUISITO (2da versión): servicio nulo o vacío
        if (servicio == null || servicio.trim().isEmpty()) {
            throw new ReservaInvalidaException("El servicio no puede estar vacío.");
        }
        // ★ EXTRA: servicio demasiado largo
        if (servicio.trim().length() > 100) {
            throw new ReservaInvalidaException("El servicio no puede superar los 100 caracteres.");
        }
        // ★ EXTRA: formato de fecha válido (dd/MM/aaaa) y fecha real (rechaza 31/02/2026, etc.)
        LocalDate fechaReserva;
        try {
            fechaReserva = LocalDate.parse(fecha.trim(), FORMATO);
        } catch (DateTimeParseException e) {
            throw new ReservaInvalidaException(
                    "Fecha inválida: '" + fecha.trim() + "'. Use el formato dd/MM/aaaa (ejemplo: 25/12/2026).");
        }
        // ★ EXTRA: no se puede reservar en el pasado
        if (fechaReserva.isBefore(LocalDate.now())) {
            throw new ReservaInvalidaException("La fecha de la reserva no puede estar en el pasado.");
        }
        // ★ EXTRA: el mismo huésped no puede tener dos reservas en la misma fecha con este recepcionista
        for (Reserva r : reservas) {
            if (r.getHuesped().getDocumento().equals(huesped.getDocumento())
                    && r.getFecha().equals(fecha.trim())) {
                throw new ReservaInvalidaException(
                        "El huésped " + huesped.getNombre() + " ya tiene una reserva para el " + fecha.trim() + ".");
            }
        }
    }

    // ★ REQUISITO (POLIMORFISMO DINÁMICO - SOBREESCRITURA) de generarReporte() con el formato del enunciado.
    // ★ Usa los getters heredados (getNombre, getId, getCargo) porque los atributos son private.
    @Override
    public String generarReporte() {
        return "=== RECEPCIONISTA ===\nNombre: " + getNombre() + "\nID: " + getId()
                + "\nCargo: " + getCargo() + "\nReservas gestionadas: " + reservas.size();
    }

    // ★ REQUISITO: getReservas() (devuelve una copia para no romper el encapsulamiento).
    public ArrayList<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }
}
