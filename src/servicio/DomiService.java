// DomiService

package servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import modelo.Repartidor;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Bici;
import modelo.Carro;
import modelo.Moto;
import modelo.Dron;


public class DomiService {
    private Map<String, Cliente> clientes =new HashMap<>();
    private Set<String> barrios = new HashSet<>();
    private Map<String, Double> distanciaBarrios = new HashMap<>();

    private List<Repartidor> repartidores = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();

    private boolean modoLluvia = false;

    // constructor DomiService para instanciar en main
    public DomiService() {
        cargarDatos();
    }

    public void cargarDatos() {
        barrios.addAll(List.of("Chapinero", "Teusaquillo", "La Candelaria", "Usaquén", "Cedritos", "Fontibón", "Suba", "Kennedy", "Bosa"));

        distanciaBarrios.put("Chapinero", 1.5);
        distanciaBarrios.put("Teusaquillo", 3.0);
        distanciaBarrios.put("La Candelaria", 4.5);
        distanciaBarrios.put("Usaquén", 6.0);
        distanciaBarrios.put("Cedritos", 8.0);
        distanciaBarrios.put("Fontibón", 11.0);
        distanciaBarrios.put("Suba", 12.0);
        distanciaBarrios.put("Kennedy", 14.0);
        distanciaBarrios.put("Bosa", 18.0);

        repartidores.add(new Bici("R-01", "Camila Torres"));
        repartidores.add(new Moto("R-02", "Andrés Mejía"));
        repartidores.add(new Carro("R-03", "Jorge Pardo"));

        clientes.put("1020304050", new Cliente("1020304050", "Laura Gómez", "3001234567", "Teusaquillo", "Calle 45 # 15-20"));
        clientes.put("1030405060", new Cliente("1030405060", "Mateo Rincón", "3109876543", "Usaquén", "Calle 15 # 10-12"));


    }

    public void registrarCliente(Cliente cliente) {

    }

    public Pedido crearPedido(String cedula, String descripcion, boolean esGrande) {
        return null;
    }

    public void entregarPedido(int numero, int calificacion) {

    }

    public void cancelarPedido(int numero) {

    }

    public String cierreDelDia() {
        return null;
    }

    //resto de metodos




}
