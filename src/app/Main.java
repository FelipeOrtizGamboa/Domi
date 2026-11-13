// Main principal Domi
package app;

import servicio.DomiService;
import modelo.Cliente;

import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu();

        DomiService servicio = new DomiService();

        int opcion = -1;

        do {
            try {
                menu.mostrarMenu(false);
                System.out.println("Ingresa un numero: ");
                //String numero = scanner.nextLine();
                opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 0:
                        System.out.print("Cerrando sistema Domi...");
                        break;
                    case 1:
                        System.out.print("Cédula: ");
                        String cedula = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Teléfono (7 a 10 dígitos): ");
                        String telefono = scanner.nextLine();
                        System.out.print("Barrio: ");
                        String barrio = scanner.nextLine();
                        System.out.print("Dirección: ");
                        String direccion = scanner.nextLine();

                        Cliente nuevoCliente = new Cliente(cedula, nombre, telefono, barrio, direccion);
                        servicio.registrarCliente(nuevoCliente);
                        System.out.println("Cliente registrado exitosamente.");
                        break;
                    case 2:
                        Collection<Cliente> listaClientes = servicio.listarClientes();
                        if (listaClientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                        } else {
                            System.out.println("\n--- LISTA DE CLIENTES ---");
                            for (Cliente c : listaClientes) {
                                System.out.println("CC: " + c.getCedula() + " | " + c.getNombre()
                                        + " | Tel: " + c.getTelefono() + " | " + c.getBarrio() + " (" + c.getDireccion() + ")");
                            }
                        }
                        break;
                    case 3:
                        System.out.print("Cédula del cliente a editar: ");
                        String cedEditar = scanner.nextLine().trim();
                        System.out.print("Nuevo teléfono: ");
                        String nuevoTel = scanner.nextLine();
                        System.out.print("Nuevo barrio: ");
                        String nuevoBarrio = scanner.nextLine();
                        System.out.print("Nueva dirección: ");
                        String nuevaDir = scanner.nextLine();

                        servicio.editarCliente(cedEditar, nuevoTel, nuevoBarrio, nuevaDir);
                        System.out.println("Cliente actualizado correctamente.");
                        break;
                    case 4:
                        System.out.print("Cédula del cliente a eliminar: ");
                        String cedEliminar = scanner.nextLine().trim();
                        servicio.eliminarCliente(cedEliminar);
                        System.out.println("Cliente eliminado.");
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    default:
                        System.out.println("valor ingresado fuera del rango 0-13");
                        break;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("ERROR: debes ingresar un numero valido");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
        scanner.close();
    }

}
