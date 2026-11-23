package com.hotel.modelo;

import com.hotel.excepciones.EmpleadoDuplicadoException;
import com.hotel.excepciones.EmpleadoNoEncontradoException;
import com.hotel.excepciones.HabitacionDuplicadaException;
import com.hotel.excepciones.HuespedNoEncontradoException;

import java.util.ArrayList;

// ★ REQUISITO (COMPOSICIÓN con Habitacion): el Hotel CREA sus habitaciones internamente;
// ★ si el hotel desaparece, sus habitaciones también.
public class Hotel {

    private String nombre;
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Empleado> empleados;

    public Hotel(String nombre) {
        // ★ EXTRA: el nombre del hotel no puede estar vacío.
        Validador.validarTexto(nombre, "El nombre del hotel");
        this.nombre = nombre.trim();
        // ★ REQUISITO: inicializa habitaciones y empleados vacíos
        this.habitaciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
        // ★ REQUISITO (COMPOSICIÓN): crea internamente las habitaciones iniciales (mínimo 3).
        this.habitaciones.add(new Habitacion("101", 2));
        this.habitaciones.add(new Habitacion("102", 3));
        this.habitaciones.add(new Habitacion("201", 4));
    }

    // ★ REQUISITO: agregarHabitacion(numero, capacidad). El Hotel crea el objeto Habitacion INTERNAMENTE
    // ★ (conserva la composición aunque App dispare la acción desde el menú, opción 10).
    public void agregarHabitacion(String numero, int capacidad) throws HabitacionDuplicadaException {
        // ★ La validación de capacidad <= 0 la hace el constructor de Habitacion (IllegalArgumentException).
        Habitacion nueva = new Habitacion(numero, capacidad);
        // ★ EXTRA: no permitir números de habitación repetidos
        if (buscarHabitacion(numero) != null) {
            throw new HabitacionDuplicadaException(numero);
        }
        habitaciones.add(nueva);
        System.out.println("Habitación '" + numero + "' creada con capacidad " + capacidad);
    }

    // ★ REQUISITO: agregarEmpleado(e).
    public void agregarEmpleado(Empleado e) throws EmpleadoDuplicadoException {
        // ★ EXTRA: empleado null
        if (e == null) {
            throw new IllegalArgumentException("Debe indicarse un empleado válido.");
        }
        // ★ EXTRA: id repetido
        for (Empleado existente : empleados) {
            if (existente.getId().equalsIgnoreCase(e.getId())) {
                throw new EmpleadoDuplicadoException(e.getId());
            }
        }
        empleados.add(e);
        System.out.println(e.getNombre() + " registrado en el hotel.");
    }

    // ★ REQUISITO: buscarHabitacion(numero) retorna la habitación o null.
    public Habitacion buscarHabitacion(String numero) {
        if (numero == null) {
            return null;
        }
        for (Habitacion h : habitaciones) {
            if (h.getNumero().equalsIgnoreCase(numero.trim())) {
                return h;
            }
        }
        return null;
    }

    // ★ REQUISITO: buscarHuesped(documento) recorre las habitaciones CAPTURANDO HuespedNoEncontradoException
    // ★ para seguir buscando; al final lanza HuespedNoEncontradoException(documento).
    public Huesped buscarHuesped(String documento) throws HuespedNoEncontradoException {
        for (Habitacion h : habitaciones) {
            try {
                return h.buscarHuesped(documento);
            } catch (HuespedNoEncontradoException e) {
                // no está en esta habitación: seguimos con la siguiente
            }
        }
        throw new HuespedNoEncontradoException(documento);
    }

    // ★ EXTRA: devuelve la habitación donde está alojado un huésped (se usa en el check-out).
    public Habitacion buscarHabitacionDeHuesped(String documento) throws HuespedNoEncontradoException {
        for (Habitacion h : habitaciones) {
            try {
                h.buscarHuesped(documento);
                return h;
            } catch (HuespedNoEncontradoException e) {
                // seguimos buscando
            }
        }
        throw new HuespedNoEncontradoException(documento);
    }

    // ★ REQUISITO: buscarRecepcionista(id). Recorre empleados; si es instanceof Recepcionista y el id coincide,
    // ★ retorna el objeto (con CAST); si no, lanza EmpleadoNoEncontradoException(id).
    public Recepcionista buscarRecepcionista(String id) throws EmpleadoNoEncontradoException {
        for (Empleado e : empleados) {
            if (e instanceof Recepcionista && e.getId().equalsIgnoreCase(id == null ? "" : id.trim())) {
                return (Recepcionista) e;
            }
        }
        throw new EmpleadoNoEncontradoException(id);
    }

    // ★ REQUISITO: listarRecepcionistas() imprime SOLO los recepcionistas del personal real
    // ★ ("id - nombre (cargo)"). Sin datos quemados: consulta la lista empleados.
    public void listarRecepcionistas() {
        boolean hay = false;
        for (Empleado e : empleados) {
            if (e instanceof Recepcionista) {
                System.out.println(e.getId() + " - " + e.getNombre() + " (" + e.getCargo() + ")");
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("No hay recepcionistas registrados.");
        }
    }

    // ★ REQUISITO: generarReporteGeneral(): ocupación de cada habitación, total de empleados y el
    // ★ generarReporte() de cada uno (POLIMORFISMO DINÁMICO: se ejecuta la versión de cada subclase).
    public void generarReporteGeneral() {
        System.out.println("=== REPORTE GENERAL: " + nombre + " ===");
        for (Habitacion h : habitaciones) {
            System.out.println("Habitación: " + h.getNumero()
                    + " | Ocupación: " + h.getHuespedes().size() + "/" + h.getCapacidad());
        }
        System.out.println("Total de empleados: " + empleados.size());
        for (Empleado e : empleados) {
            System.out.println(e.generarReporte());
            System.out.println("-----------------------");
        }
    }

    // ★ REQUISITO: getters de habitaciones y empleados (más getNombre).
    public String getNombre() {
        return nombre;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return new ArrayList<>(habitaciones);
    }

    public ArrayList<Empleado> getEmpleados() {
        return new ArrayList<>(empleados);
    }
}
