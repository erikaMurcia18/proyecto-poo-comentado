package com.hotel.modelo;

import com.hotel.excepciones.HabitacionLlenaException;
import com.hotel.excepciones.HuespedNoEncontradoException;
import com.hotel.excepciones.HuespedYaAlojadoException;

import java.util.ArrayList;

// ★ REQUISITO (AGREGACIÓN con Huesped): la habitación puede existir vacía y los huéspedes
// ★ pueden existir sin habitación (la habitación solo guarda referencias, no los crea).
public class Habitacion {

    private String numero;
    private int capacidad;

    // ★ REQUISITO: ArrayList<Huesped> (tipo ABSTRACTO) -> acepta regulares y VIP (POLIMORFISMO).
    private ArrayList<Huesped> huespedes;

    public Habitacion(String numero, int capacidad) {
        // ★ EXTRA: número de habitación válido.
        Validador.validarNumeroHabitacion(numero);
        // ★ REQUISITO: SI capacidad <= 0 -> IllegalArgumentException("Capacidad debe ser mayor a 0.")
        if (capacidad <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser mayor a 0.");
        }
        // ★ EXTRA: capacidad máxima razonable.
        if (capacidad > 20) {
            throw new IllegalArgumentException("La capacidad máxima por habitación es 20.");
        }
        this.numero = numero;
        this.capacidad = capacidad;
        this.huespedes = new ArrayList<>(); // ★ REQUISITO: inicializa huespedes vacío
    }

    // ★ REQUISITO: alojarHuesped(h). Lanza HabitacionLlenaException si no hay cupo.
    public void alojarHuesped(Huesped h) throws HabitacionLlenaException, HuespedYaAlojadoException {
        // ★ EXTRA: huésped null
        if (h == null) {
            throw new IllegalArgumentException("Debe indicarse un huésped válido.");
        }
        // ★ REQUISITO: si huespedes.size() >= capacidad -> HabitacionLlenaException con el mensaje exigido
        if (huespedes.size() >= capacidad) {
            throw new HabitacionLlenaException("Habitación " + numero + " sin cupo disponible.");
        }
        // ★ EXTRA: el mismo huésped no puede estar dos veces en la misma habitación
        for (Huesped existente : huespedes) {
            if (existente.getDocumento().equals(h.getDocumento())) {
                throw new HuespedYaAlojadoException(
                        "El huésped " + h.getNombre() + " ya está alojado en la habitación " + numero + ".");
            }
        }
        huespedes.add(h);
        System.out.println("Huésped " + h.getNombre() + " alojado en habitación " + numero);
    }

    // ★ REQUISITO: liberarHuesped(documento). Si no lo encuentra -> HuespedNoEncontradoException.
    public void liberarHuesped(String documento) throws HuespedNoEncontradoException {
        // ★ buscarHuesped ya lanza HuespedNoEncontradoException(documento) si no existe.
        Huesped h = buscarHuesped(documento);
        huespedes.remove(h);
        System.out.println("Check-out realizado.");
    }

    // ★ REQUISITO: buscarHuesped(documento). Retorna el huésped o lanza HuespedNoEncontradoException.
    public Huesped buscarHuesped(String documento) throws HuespedNoEncontradoException {
        for (Huesped h : huespedes) {
            if (h.getDocumento().equals(documento)) {
                return h;
            }
        }
        throw new HuespedNoEncontradoException(documento);
    }

    // ★ REQUISITO: listarHuespedes(). POLIMORFISMO DINÁMICO: h.obtenerInfo() se resuelve según el tipo real.
    public void listarHuespedes() {
        if (huespedes.isEmpty()) {
            System.out.println("No hay huéspedes en esta habitación.");
            return;
        }
        for (Huesped h : huespedes) {
            System.out.println(h.obtenerInfo());
        }
    }

    // ★ REQUISITO: getters de numero, capacidad y huespedes.
    public String getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Huesped> getHuespedes() {
        return new ArrayList<>(huespedes);
    }
}
