package modelo;

public class Cliente {
    // Datos del cliente
    private String cedula;
    private String nombre;
    private String telefono;
    private String barrio;
    private String direccion;

    // Constructor de cliente. recibe los datos
    public Cliente(String cedula, String nombre, String telefono, String barrio, String direccion) {
        // La cédula no se edita, por eso se válida aquí // this.cedula = el atributo del objeto; cedula = el dato que llegó
        this.cedula = validarTextoNoVacio(cedula, "cédula");
        // Se usa setters para validar
        setNombre(nombre);
        setTelefono(telefono);
        setBarrio(barrio);
        setDireccion(direccion);
    }

    // Getters. para leer los datos desde otras clases
    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    // Setters. validan y, si algo está mal, lanzan IllegalArgumentException con un mensaje claro
    // no hay setCedula porque la cédula no se edita

    public void setNombre(String nombre) {
        // Revisa que no esté vacío, le quita los espacios y lo guarda
        this.nombre = validarTextoNoVacio(nombre, "nombre");
    }

    public void setTelefono(String telefono) {
        // Evitar null
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }

        // Quitar espacios sobrantes
        String telefonoSinEspacios = telefono.trim();

        // Verificar que tenga entre 7 y 10 dígitos
        if (telefonoSinEspacios.length() < 7 || telefonoSinEspacios.length() > 10) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 10 dígitos");
        }

        // Revisar que el teléfono solo tenga números. recorre el texto letra por letra. uso de Character.isDigit
        for (int i = 0; i < telefonoSinEspacios.length(); i++) {
            if (!Character.isDigit(telefonoSinEspacios.charAt(i))) {
                throw new IllegalArgumentException("El teléfono solo puede contener números");
            }
        }
        // Se guarda si pasa todas las pruebas
        this.telefono = telefonoSinEspacios;
    }

    public void setBarrio(String barrio) {
        // Revisa que no esté vacío, le quita los espacios y lo guarda
        this.barrio = validarTextoNoVacio(barrio, "barrio");
    }

    public void setDireccion(String direccion) {
        // Revisa que no esté vacío, le quita los espacios y lo guarda
        this.direccion = validarTextoNoVacio(direccion, "dirección");
    }

    // Método propio para evitar texto vacío
    private String validarTextoNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
        return valor.trim();
    }

    // Define cómo se ve el cliente al imprimirlo
    @Override
    public String toString() {
        return nombre + " · C.C. " + cedula + " · Tel. " + telefono + " · " + barrio + " · " + direccion;
    }
}
