package com.hotel.modelo;

// ★ REQUISITO (Clase de utilidad Validador): métodos ESTÁTICOS reutilizados por varias clases.
// ★ REQUISITO (Modificadores de acceso): constructor PRIVATE (no se instancia) y métodos public static.
public final class Validador {

    // ★ Constructor private: nadie puede hacer "new Validador()".
    private Validador() {
    }

    // ★ REQUISITO: validarNombre(nombre). Se usa en los constructores de Empleado y Huesped.
    public static void validarNombre(String nombre) {
        // ★ Pseudocódigo: SI nombre es nulo o vacío -> IllegalArgumentException("El nombre no puede estar vacío.")
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        // ★ Pseudocódigo: SI nombre contiene algún dígito (0-9) -> IllegalArgumentException con el mensaje exigido
        for (int i = 0; i < nombre.length(); i++) {
            char c = nombre.charAt(i);
            if (c >= '0' && c <= '9') {
                throw new IllegalArgumentException("El nombre no puede contener números: " + nombre);
            }
        }
        // ★ EXTRA: solo letras, espacios, punto, apóstrofe y guion (bloquea símbolos como @ # $ %).
        if (!nombre.matches("[\\p{L} .'-]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios: " + nombre);
        }
        // ★ EXTRA: longitud máxima razonable.
        if (nombre.trim().length() > 60) {
            throw new IllegalArgumentException("El nombre no puede superar los 60 caracteres.");
        }
    }

    // ★ REQUISITO: validarDocumento(documento). Se usa en el constructor de Huesped.
    public static void validarDocumento(String documento) {
        // ★ Pseudocódigo: SI documento es nulo o su longitud no está entre 5 y 12 -> IllegalArgumentException
        if (documento == null || documento.length() < 5 || documento.length() > 12) {
            throw new IllegalArgumentException("El documento debe tener entre 5 y 12 dígitos.");
        }
        // ★ Pseudocódigo: SI algún carácter del documento no es dígito -> IllegalArgumentException
        for (int i = 0; i < documento.length(); i++) {
            char c = documento.charAt(i);
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("El documento solo puede contener números.");
            }
        }
    }

    // ★ EXTRA: valida cualquier texto obligatorio (id, cargo, zona, nombre del hotel...).
    public static void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
    }

    // ★ EXTRA: valida el número de habitación (alfanumérico, 1 a 10 caracteres).
    public static void validarNumeroHabitacion(String numero) {
        if (numero == null || !numero.matches("[A-Za-z0-9]{1,10}")) {
            throw new IllegalArgumentException(
                    "El número de habitación debe tener entre 1 y 10 caracteres alfanuméricos.");
        }
    }
}
