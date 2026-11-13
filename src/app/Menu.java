// app/Menu

package app;

public class Menu {

    public void mostrarMenu(boolean modoLluvia) {
        System.out.println("============== DOMI · MENU  ==============");
        if (modoLluvia) {
            System.out.println("🌧️ '''' MODO LLUVIA ACTIVO '''' 🌧️");
        }
        System.out.println("CLIENTES        1. Registrar  2. Listar");
        System.out.println("                3. Editar     4. Eliminar");
        System.out.println("---");
        System.out.println("REPARTIDORES    5. Registrar  6. Listar");
        System.out.println("                7. Cambiar disponibilidad");
        System.out.println("---");
        System.out.println("PEDIDOS         8. Crear pedido (cotiza y asigna)");
        System.out.println("                9. Entregar   10. Cancelar");
        System.out.println("                11. Listar pedidos");
        System.out.println("---");
        System.out.println("OPERACIÓN       12. Activar / Desactivar lluvia");
        System.out.println("                13. Cierre del día");
        System.out.println("---");
        System.out.println("0. Salir");
        System.out.println("====================================================");
    }
}
