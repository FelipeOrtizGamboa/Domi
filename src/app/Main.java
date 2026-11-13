// Main principal Domi
package app;

import modelo.*;
import servicio.DomiService;
import java.util.List;

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
                menu.mostrarMenu(servicio.isModoLluvia());
                System.out.println("Ingresa un numero: ");
                //String numero = scanner.nextLine();
                opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 0:
                        System.out.print("Cerrando sistema Domi...");
                        break;
                    case 1: //registrar clientes
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
                    case 2: //listar clientes
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
                    case 3: // editar cliente
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
                    case 4: // eliminar cliente
                        System.out.print("Cédula del cliente a eliminar: ");
                        String cedEliminar = scanner.nextLine().trim();
                        servicio.eliminarCliente(cedEliminar);
                        System.out.println("Cliente eliminado.");
                        break;
                    case 5: //registrar repartidor
                        System.out.print("Código (ej. R-04): ");
                        String codRep = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nomRep = scanner.nextLine();
                        System.out.println("Tipo de vehículo: 1. Bici | 2. Moto | 3. Carro | 4. Dron");
                        System.out.print("Elige el tipo: ");
                        int tipoRep = Integer.parseInt(scanner.nextLine().trim());

                        Repartidor repartidor;
                        switch (tipoRep) {
                            case 1 -> repartidor = new Bici(codRep, nomRep);
                            case 2 -> repartidor = new Moto(codRep, nomRep);
                            case 3 -> repartidor = new Carro(codRep, nomRep);
                            case 4 -> repartidor = new Dron(codRep, nomRep);
                            default -> throw new IllegalArgumentException("Tipo de repartidor inválido.");
                        }
                        servicio.registrarRepartidor(repartidor);
                        System.out.println("Repartidor registrado exitosamente.");
                        break;
                    case 6: //listar repartidores
                        List<Repartidor> listaRep = servicio.listarRepartidores();
                        if (listaRep.isEmpty()) {
                            System.out.println("No hay repartidores registrados.");
                        } else {
                            System.out.println("\n--- LISTA DE REPARTIDORES ---");
                            for (Repartidor r : listaRep) {
                                System.out.printf("%s | %s (%s) | Disponible: %s | Promedio: %.2f%n",
                                        r.getCodigo(), r.getNombre(),
                                        r.isDisponible() ? "Sí" : "No", r.obtenerPromedio());
                            }
                        }
                        break;
                    case 7: //cambiar disponibilidad
                        System.out.print("Código del repartidor: ");
                        String codDisp = scanner.nextLine();
                        Repartidor actualizado = servicio.cambiarDisponibilidad(codDisp);
                        System.out.println("Disponibilidad de " + actualizado.getNombre()
                                + " cambiada a: " + (actualizado.isDisponible() ? "Disponible" : "No disponible"));
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
                        boolean lluviaActiva = servicio.alternarModoLluvia();
                        System.out.println(lluviaActiva
                                ? "🌧️ MODO LLUVIA ACTIVADO (Bicis y Drones restringidos, +20% en tarifas)."
                                : "☀️ MODO LLUVIA DESACTIVADO (Operación normal).");
                        break;
                    case 13:
                        System.out.println(servicio.cierreDelDia());
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
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion != 0);
        scanner.close();
    }

}
