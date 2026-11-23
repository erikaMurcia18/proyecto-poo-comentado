package com.hotel.app;

import com.hotel.excepciones.EmpleadoDuplicadoException;
import com.hotel.excepciones.EmpleadoNoEncontradoException;
import com.hotel.excepciones.HabitacionDuplicadaException;
import com.hotel.excepciones.HabitacionLlenaException;
import com.hotel.excepciones.HabitacionNoEncontradaException;
import com.hotel.excepciones.HuespedDuplicadoException;
import com.hotel.excepciones.HuespedNoEncontradoException;
import com.hotel.excepciones.HuespedYaAlojadoException;
import com.hotel.excepciones.ReservaInvalidaException;
import com.hotel.modelo.Camarero;
import com.hotel.modelo.Habitacion;
import com.hotel.modelo.Hotel;
import com.hotel.modelo.Huesped;
import com.hotel.modelo.HuespedRegular;
import com.hotel.modelo.HuespedVIP;
import com.hotel.modelo.Recepcionista;
import com.hotel.modelo.Reserva;
import com.hotel.modelo.Validador;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// ★ REQUISITO (Paquete app): clase App con el menú principal por consola.
public class App {

    // ★ REQUISITO (Método auxiliar obligatorio): leerEntero(sc, mensaje).
    // ★ Insiste hasta recibir un entero, captura InputMismatchException INTERNAMENTE y limpia el buffer
    // ★ con sc.nextLine(). Se usa en TODAS las lecturas numéricas (opción, tipo, edad, noches, descuento, capacidad).
    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = sc.nextInt();
                sc.nextLine(); // limpia el salto de línea que deja nextInt()
                return numero;
            } catch (InputMismatchException e) {
                sc.nextLine(); // descarta la entrada inválida (letras, decimales, número demasiado grande...)
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }

    // ★ EXTRA: lectura de texto (quita espacios al inicio y al final).
    public static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ★ REQUISITO (main): lista temporal huespedesRegistrados (ArrayList<Huesped>)
        ArrayList<Huesped> huespedesRegistrados = new ArrayList<>();

        // ★ REQUISITO (main): crear el hotel "Hotel Sabana Real"
        Hotel hotel = new Hotel("Hotel Sabana Real");

        // ★ REQUISITO (main): crear recepcionistas, camarero y registrar el personal en el hotel
        try {
            Recepcionista r1 = new Recepcionista("Camila Prieto", "R001", "Recepción día");
            Recepcionista r2 = new Recepcionista("Andrés Mora", "R002", "Recepción noche");
            Camarero c1 = new Camarero("Luz Marina Díaz", "C001", "Housekeeping", "Piso 1");
            hotel.agregarEmpleado(r1);
            hotel.agregarEmpleado(r2);
            hotel.agregarEmpleado(c1);
        } catch (IllegalArgumentException | EmpleadoDuplicadoException e) {
            System.out.println("Error al crear el personal inicial: " + e.getMessage());
            sc.close();
            return;
        }

        int opcion = -1;
        try {
            // ★ REQUISITO (main): REPETIR ... HASTA QUE opción == 0
            do {
                mostrarMenu();
                opcion = leerEntero(sc, "Seleccione una opción: ");

                // ★ REQUISITO (Excepciones capturadas correctamente): cada opción se ejecuta dentro de un try/catch.
                try {
                    procesarOpcion(opcion, sc, hotel, huespedesRegistrados);

                // ★ Excepciones personalizadas (las 4 del enunciado + las extra)
                } catch (HabitacionLlenaException | HuespedNoEncontradoException
                         | EmpleadoNoEncontradoException | ReservaInvalidaException
                         | HabitacionNoEncontradaException | HuespedDuplicadoException
                         | HabitacionDuplicadaException | HuespedYaAlojadoException e) {
                    System.out.println("Error: " + e.getMessage());
                // ★ IllegalArgumentException: nombre/documento/edad/noches/descuento/capacidad inválidos
                } catch (IllegalArgumentException e) {
                    System.out.println("Error de validación: " + e.getMessage());
                // ★ EXTRA: si se cierra la entrada (Ctrl+D / Ctrl+Z) se propaga y el programa termina limpio
                } catch (NoSuchElementException e) {
                    throw e;
                // ★ EXTRA: red de seguridad para cualquier error inesperado (el programa nunca se cae)
                } catch (RuntimeException e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                }

            } while (opcion != 0);

        } catch (NoSuchElementException e) {
            System.out.println("\nSe cerró la entrada de datos. Finalizando el programa.");
        } finally {
            sc.close();
        }
    }

    // ★ REQUISITO (main): SEGÚN opción HACER ... (cada opción de la tabla del enunciado)
    private static void procesarOpcion(int opcion, Scanner sc, Hotel hotel, ArrayList<Huesped> registrados)
            throws HabitacionLlenaException, HuespedNoEncontradoException, EmpleadoNoEncontradoException,
            ReservaInvalidaException, HabitacionNoEncontradaException, HuespedDuplicadoException,
            HabitacionDuplicadaException, HuespedYaAlojadoException {

        switch (opcion) {
            case 1:
                registrarHuesped(sc, registrados);
                break;
            case 2:
                listarHuespedesRegistrados(registrados);
                break;
            case 3:
                alojarHuesped(sc, hotel, registrados);
                break;
            case 4:
                hacerReserva(sc, hotel);
                break;
            case 5:
                verHuespedesDeHabitacion(sc, hotel);
                break;
            case 6:
                verReservasDeRecepcionista(sc, hotel);
                break;
            case 7:
                checkOut(sc, hotel);
                break;
            case 8:
                buscarHuesped(sc, hotel);
                break;
            case 9:
                hotel.generarReporteGeneral(); // ★ Opción 9: reporte general
                break;
            case 10:
                crearHabitacion(sc, hotel);
                break;
            case 0:
                System.out.println("\nGracias por visitar Hotel Sabana Real.");
                break;
            default:
                // ★ EXTRA: opción fuera del menú
                System.out.println("Opción no válida. Elija un número entre 0 y 10.");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n==================================");
        System.out.println("        HOTEL SABANA REAL");
        System.out.println("==================================");
        System.out.println("1. Registrar huésped");
        System.out.println("2. Listar huéspedes registrados");
        System.out.println("3. Alojar huésped en una habitación");
        System.out.println("4. Hacer una reserva");
        System.out.println("5. Ver huéspedes de una habitación");
        System.out.println("6. Ver reservas de un recepcionista");
        System.out.println("7. Check-out de un huésped");
        System.out.println("8. Buscar huésped por documento");
        System.out.println("9. Reporte general del hotel");
        System.out.println("10. Crear nueva habitación");
        System.out.println("0. Salir");
        System.out.println("==================================");
    }

    // ★ OPCIÓN 1: Registrar huésped (1=Regular, 2=VIP)
    private static void registrarHuesped(Scanner sc, ArrayList<Huesped> registrados)
            throws HuespedDuplicadoException {
        System.out.println("\n--- REGISTRO DE HUÉSPED ---");

        String nombre = leerTexto(sc, "Nombre completo: ");
        Validador.validarNombre(nombre);            // ★ nombre con números / vacío -> IllegalArgumentException

        String documento = leerTexto(sc, "Número de documento: ");
        Validador.validarDocumento(documento);      // ★ documento no numérico o de longitud errónea

        // ★ EXTRA: documento ya registrado
        for (Huesped h : registrados) {
            if (h.getDocumento().equals(documento)) {
                throw new HuespedDuplicadoException(documento);
            }
        }

        int edad = leerEntero(sc, "Edad: ");        // ★ la edad se valida en el constructor de Huesped

        System.out.println("1. Regular");
        System.out.println("2. VIP");
        int tipo = leerEntero(sc, "Tipo de huésped: ");

        // ★ REQUISITO: tipo distinto de 1 o 2 muestra "Tipo inválido" y vuelve al menú
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido");
            return;
        }

        int noches = leerEntero(sc, "Número de noches: ");

        Huesped nuevo;
        if (tipo == 1) {
            nuevo = new HuespedRegular(nombre, documento, edad, noches);
        } else {
            int descuento = leerEntero(sc, "Descuento VIP (0 a 50): ");
            nuevo = new HuespedVIP(nombre, documento, edad, noches, descuento);
        }

        registrados.add(nuevo);
        System.out.println("Huésped registrado correctamente.");
        System.out.println(nuevo.obtenerInfo());
    }

    // ★ OPCIÓN 2: Listar huéspedes registrados con obtenerInfo() (POLIMORFISMO DINÁMICO),
    // ★ sin importar si ya tienen habitación.
    private static void listarHuespedesRegistrados(ArrayList<Huesped> registrados) {
        System.out.println("\n--- HUÉSPEDES REGISTRADOS ---");
        if (registrados.isEmpty()) {
            System.out.println("No hay huéspedes registrados.");
            return;
        }
        for (Huesped h : registrados) {
            System.out.println(h.obtenerInfo());
        }
    }

    // ★ OPCIÓN 3: Alojar huésped. Controla: huésped no registrado, habitación inexistente y HabitacionLlenaException.
    private static void alojarHuesped(Scanner sc, Hotel hotel, ArrayList<Huesped> registrados)
            throws HuespedNoEncontradoException, HabitacionNoEncontradaException,
            HabitacionLlenaException, HuespedYaAlojadoException {
        System.out.println("\n--- ALOJAR HUÉSPED ---");

        String documento = leerTexto(sc, "Documento del huésped: ");
        Validador.validarDocumento(documento);

        // ★ huésped no registrado -> HuespedNoEncontradoException
        Huesped huesped = null;
        for (Huesped h : registrados) {
            if (h.getDocumento().equals(documento)) {
                huesped = h;
            }
        }
        if (huesped == null) {
            throw new HuespedNoEncontradoException(documento);
        }

        // ★ EXTRA: el huésped no puede estar alojado en otra habitación al mismo tiempo
        try {
            Habitacion actual = hotel.buscarHabitacionDeHuesped(documento);
            throw new HuespedYaAlojadoException("El huésped " + huesped.getNombre()
                    + " ya está alojado en la habitación " + actual.getNumero() + ".");
        } catch (HuespedNoEncontradoException e) {
            // no está alojado en ninguna habitación: se puede continuar
        }

        mostrarHabitaciones(hotel);
        String numero = leerTexto(sc, "Número de habitación: ");
        Habitacion habitacion = obtenerHabitacion(hotel, numero); // ★ habitación inexistente
        habitacion.alojarHuesped(huesped);                        // ★ HabitacionLlenaException
    }

    // ★ OPCIÓN 4: Hacer una reserva (sigue el PSEUDOCÓDIGO del enunciado)
    private static void hacerReserva(Scanner sc, Hotel hotel)
            throws EmpleadoNoEncontradoException, HuespedNoEncontradoException, ReservaInvalidaException {
        System.out.println("\n--- HACER RESERVA ---");

        hotel.listarRecepcionistas();
        String id = leerTexto(sc, "ID del recepcionista: ");

        // ★ buscarRecepcionista puede lanzar EmpleadoNoEncontradoException
        Recepcionista recepcionista = hotel.buscarRecepcionista(id);

        String documento = leerTexto(sc, "Documento del huésped: ");
        Validador.validarDocumento(documento);
        // ★ DECISIÓN DEL EQUIPO (documentar en README): el huésped debe estar ALOJADO primero (opción 3),
        // ★ porque Hotel.buscarHuesped solo encuentra huéspedes que ya están en una habitación.
        Huesped huesped = hotel.buscarHuesped(documento); // ★ puede lanzar HuespedNoEncontradoException

        String fecha = leerTexto(sc, "Fecha de la reserva (dd/MM/aaaa): ");

        // ★ Preguntar si agrega servicio (s/n); se repite hasta recibir s o n
        String respuesta;
        do {
            respuesta = leerTexto(sc, "¿Desea agregar un servicio? (s/n): ").toLowerCase();
            if (!respuesta.equals("s") && !respuesta.equals("n")) {
                System.out.println("Responda únicamente con 's' o 'n'.");
            }
        } while (!respuesta.equals("s") && !respuesta.equals("n"));

        if (respuesta.equals("s")) {
            String servicio = leerTexto(sc, "Servicio: ");
            // ★ Llama a la SOBRECARGA de 3 parámetros (POLIMORFISMO ESTÁTICO)
            recepcionista.hacerReserva(fecha, servicio, huesped);
        } else {
            // ★ Llama a la SOBRECARGA de 2 parámetros (POLIMORFISMO ESTÁTICO)
            recepcionista.hacerReserva(fecha, huesped);
        }
    }

    // ★ OPCIÓN 5: Ver huéspedes de una habitación. Controla habitación inexistente.
    private static void verHuespedesDeHabitacion(Scanner sc, Hotel hotel)
            throws HabitacionNoEncontradaException {
        System.out.println("\n--- HUÉSPEDES DE UNA HABITACIÓN ---");
        mostrarHabitaciones(hotel);
        String numero = leerTexto(sc, "Número de habitación: ");
        Habitacion habitacion = obtenerHabitacion(hotel, numero);
        habitacion.listarHuespedes();
    }

    // ★ OPCIÓN 6: Ver reservas de un recepcionista. Controla EmpleadoNoEncontradoException.
    private static void verReservasDeRecepcionista(Scanner sc, Hotel hotel)
            throws EmpleadoNoEncontradoException {
        System.out.println("\n--- RESERVAS DE UN RECEPCIONISTA ---");
        hotel.listarRecepcionistas();
        String id = leerTexto(sc, "ID del recepcionista: ");
        Recepcionista recepcionista = hotel.buscarRecepcionista(id);

        ArrayList<Reserva> reservas = recepcionista.getReservas();
        if (reservas.isEmpty()) {
            System.out.println(recepcionista.getNombre() + " no tiene reservas.");
            return;
        }
        for (Reserva r : reservas) {
            System.out.println(r.getInfo());
        }
    }

    // ★ OPCIÓN 7: Check-out. Controla HuespedNoEncontradoException.
    private static void checkOut(Scanner sc, Hotel hotel) throws HuespedNoEncontradoException {
        System.out.println("\n--- CHECK-OUT ---");
        String documento = leerTexto(sc, "Documento del huésped: ");
        Validador.validarDocumento(documento);
        Habitacion habitacion = hotel.buscarHabitacionDeHuesped(documento);
        habitacion.liberarHuesped(documento);
    }

    // ★ OPCIÓN 8: Buscar huésped por documento. Controla HuespedNoEncontradoException.
    private static void buscarHuesped(Scanner sc, Hotel hotel) throws HuespedNoEncontradoException {
        System.out.println("\n--- BUSCAR HUÉSPED ---");
        String documento = leerTexto(sc, "Documento del huésped: ");
        Validador.validarDocumento(documento);
        Huesped huesped = hotel.buscarHuesped(documento);
        System.out.println(huesped.obtenerInfo());
    }

    // ★ OPCIÓN 10: Crear nueva habitación en tiempo de ejecución.
    // ★ Captura IllegalArgumentException (capacidad <= 0) en el try/catch del main.
    private static void crearHabitacion(Scanner sc, Hotel hotel) throws HabitacionDuplicadaException {
        System.out.println("\n--- CREAR HABITACIÓN ---");
        String numero = leerTexto(sc, "Número de habitación: ");
        int capacidad = leerEntero(sc, "Capacidad: ");
        hotel.agregarHabitacion(numero, capacidad); // ★ el Hotel crea la Habitacion (COMPOSICIÓN)
    }

    // ★ EXTRA: muestra las habitaciones con su ocupación para que el usuario elija.
    private static void mostrarHabitaciones(Hotel hotel) {
        System.out.println("Habitaciones disponibles:");
        for (Habitacion h : hotel.getHabitaciones()) {
            System.out.println("  " + h.getNumero() + " (ocupación " + h.getHuespedes().size()
                    + "/" + h.getCapacidad() + ")");
        }
    }

    // ★ EXTRA: convierte el null de Hotel.buscarHabitacion en una excepción con mensaje claro.
    private static Habitacion obtenerHabitacion(Hotel hotel, String numero)
            throws HabitacionNoEncontradaException {
        Habitacion habitacion = hotel.buscarHabitacion(numero);
        if (habitacion == null) {
            throw new HabitacionNoEncontradaException(numero);
        }
        return habitacion;
    }
}
