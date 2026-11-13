// DomiService

package servicio;

import java.util.*;

import modelo.*;


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

    private String normalizarBarrio(String barrio) {
        if (barrio == null || barrio.isBlank()) {
            throw new IllegalArgumentException("El barrio no puede estar vacío.");
        }
        String limpio = barrio.trim();
        for (String b : barrios) {
            if (b.equalsIgnoreCase(limpio)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Sin cobertura en el barrio: " + limpio);
    }

    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Datos del ciente invalidos");
        }
        if (clientes.containsKey(cliente.getCedula())) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con la cedula" + cliente.getCedula());
        }
        String barrioValido = normalizarBarrio(cliente.getBarrio());
        cliente.setBarrio(barrioValido);
        clientes.put(cliente.getCedula(), cliente);
    }

    public Collection<Cliente> listarClientes() {
        return clientes.values();
    }

    public void editarCliente(String cedula, String nuevoTelefono, String nuevoBarrio, String nuevaDireccion) {
        Cliente cliente = clientes.get(cedula);
        if (cliente == null) {
            throw new IllegalArgumentException("No se encontró ningún cliente con cédula: " + cedula);
        }
        String barrioValido = normalizarBarrio(nuevoBarrio);
        cliente.setTelefono(nuevoTelefono);
        cliente.setBarrio(barrioValido);
        cliente.setDireccion(nuevaDireccion);
    }

    public void eliminarCliente(String cedula) {
        if (!clientes.containsKey(cedula)) {
            throw new IllegalArgumentException("No se encontró ningún cliente con cédula: " + cedula);
        }
        for (Pedido p : pedidos) {
            if (p.getCliente().getCedula().equals(cedula) && p.getEstado() == Pedido.EstadoPedido.EN_CAMINO) {
                throw new IllegalArgumentException("No se puede eliminar el cliente porque tiene un pedido EN_CAMINO (#" + p.getNumero() + ").");
            }
        }
        clientes.remove(cedula);
    }
    public ArrayList<Cotizacion> cotizarPedido(String cedula, boolean grande) {
        Cliente cliente = buscarCliente(cedula);
        double km = obtenerKm(cliente.getBarrio());

        ArrayList<Cotizacion> cotizaciones = new ArrayList<>();
        for (Repartidor r : repartidores) {
            if (r.isDisponible() && r.puedeLlevar(km, grande, modoLluvia)) {
                cotizaciones.add(new Cotizacion(r, r.cotizar(km, modoLluvia), r.calcularTiempo(km)));
            }
        }
        return cotizaciones;
    }
    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    public Cliente buscarCliente(String cedula) {
        Cliente cliente = (cedula == null) ? null : clientes.get(cedula.trim());
        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con la cédula " + cedula + ".");
        }
        return cliente;
    }
    // Los km salen del mapa, nunca los escribe el operador.
    public double obtenerKm(String barrio) {private HashMap<String, Double> distancias = new HashMap<>();
        return distanciaBarrios.get(Barrios.valueOf(barrio));
    }

    public void registrarRepartidor(Repartidor nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("Datos de repartidor no validos");
        }
        for (Repartidor r : repartidores) {
            if (r.getCodigo().equalsIgnoreCase(nuevo.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un repartidor con el código: " + nuevo.getCodigo());
            }
        }
        repartidores.add(nuevo);
    }

    public List<Repartidor> listarRepartidores() {
        return repartidores;
    }

    public Repartidor cambiarDisponibilidad(String codigo) {
        for (Repartidor r : repartidores) {
            if (r.getCodigo().equalsIgnoreCase(codigo.trim())) {
                r.setDisponible(!r.isDisponible());
                return r;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún repartidor con código: " + codigo);
    }



    public Pedido crearPedido(String cedula, String descripcion, boolean esGrande) {
        if (estaVacio(descripcion)) {
            throw new IllegalArgumentException("La descripción del pedido no puede estar vacía.");
        }
        ArrayList<Cotizacion> cotizaciones = cotizarPedido(cedula, esGrande);
        if (cotizaciones.isEmpty()) {
            throw new IllegalArgumentException("Ningún repartidor disponible puede llevar este pedido. No se creó.");
        }

        // Igual que buscar el número menor de una lista: se guarda el primero
        // como "el mejor hasta ahora" y se compara con cada uno de los demás.
        Cotizacion mejor = cotizaciones.get(0);
        for (Cotizacion c : cotizaciones) {
            if (c.esMejorQue(mejor)) {
                mejor = c;
            }
        }

        Cliente cliente = buscarCliente(cedula);
        Repartidor elegido = mejor.getRepartidor();
        Pedido pedido = new Pedido(cliente, descripcion, esGrande, obtenerKm(cliente.getBarrio()),
                elegido, mejor.getCosto(), mejor.getTiempo());

        elegido.setDisponible(false);
        pedidos.add(pedido);

        return pedido;
    }

    public void entregarPedido(int numero, int calificacion) {

    }

    public void cancelarPedido(int numero) {

    }

    public boolean alternarModoLluvia() {
        this.modoLluvia = !this.modoLluvia;
        return this.modoLluvia;
    }

    public boolean isModoLluvia() {
        return modoLluvia;
    }

    public String cierreDelDia() {
        return null;
    }

    //resto de metodos




}
