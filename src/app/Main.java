// Main principal Domi
package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu();

        int opcion = -1;

        do {
            try {
                menu.mostrarMenu(false);
                System.out.println("Ingresa un numero: ");
                //String numero = scanner.nextLine();
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
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
