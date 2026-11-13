package modelo;

public class Cliente {

    private String cedula;
    private String nombre;
    private String telefono;
    private String barrio;
    private String direccion;


    public Cliente(String cedula, String nombre, String telefono, String barrio, String direccion) {

        this.cedula = validarTextoNoVacio(cedula, "cédula");

        setNombre(nombre);
        setTelefono(telefono);
        setBarrio(barrio);
        setDireccion(direccion);
    }


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


    public void setNombre(String nombre) {

        this.nombre = validarTextoNoVacio(nombre, "nombre");
    }

    public void setTelefono(String telefono) {

        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }


        String telefonoSinEspacios = telefono.trim();


        if (telefonoSinEspacios.length() < 7 || telefonoSinEspacios.length() > 10) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 10 dígitos");
        }


        for (int i = 0; i < telefonoSinEspacios.length(); i++) {
            if (!Character.isDigit(telefonoSinEspacios.charAt(i))) {
                throw new IllegalArgumentException("El teléfono solo puede contener números");
            }
        }

        this.telefono = telefonoSinEspacios;
    }

    public void setBarrio(String barrio) {

        this.barrio = validarTextoNoVacio(barrio, "barrio");
    }

    public void setDireccion(String direccion) {

        this.direccion = validarTextoNoVacio(direccion, "dirección");
    }


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
