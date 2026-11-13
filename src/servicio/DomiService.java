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
            throw new IllegalArgumentException("Ya existe un cliente registrado con la cédula " + cliente.getCedula());
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

    public double obtenerKm(String barrio) {
        Double km = distanciaBarrios.get(barrio);
        if (km == null) {
            throw new IllegalArgumentException("Sin cobertura en el barrio: " + barrio);
        }
        return km;
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

    public Pedido buscarPedido(int numero) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == numero) {
                return p;
            }
        }
        throw new IllegalArgumentException("No existe un pedido con el número " + numero + ".");
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }

    public void entregarPedido(int numero, int calificacion) {
        Pedido pedido = buscarPedido(numero);
        if (pedido.getEstado() != Pedido.EstadoPedido.EN_CAMINO) {
            throw new IllegalArgumentException("Solo se puede entregar un pedido EN_CAMINO. Este está " + pedido.getEstado() + ".");
        }
        Repartidor repartidor = pedido.getRepartidorAsignado();
        repartidor.agregarCalificacion(calificacion);
        pedido.setEstado(Pedido.EstadoPedido.ENTREGADO);
        repartidor.setDisponible(true);
    }

    public void cancelarPedido(int numero) {
        Pedido pedido = buscarPedido(numero);
        if (pedido.getEstado() != Pedido.EstadoPedido.EN_CAMINO) {
            throw new IllegalArgumentException("Solo se puede cancelar un pedido EN_CAMINO. Este está " + pedido.getEstado() + ".");
        }
        pedido.setEstado(Pedido.EstadoPedido.CANCELADO);
        pedido.getRepartidorAsignado().setDisponible(true);
    }

    public boolean alternarModoLluvia() {
        this.modoLluvia = !this.modoLluvia;
        return this.modoLluvia;
    }

    public boolean isModoLluvia() {
        return modoLluvia;
    }

    public String cierreDelDia() {
        // Mapas: la llave es la categoría y el valor es el acumulado
        Map<Pedido.EstadoPedido, Integer> conteoPorEstado = new LinkedHashMap<>();
        for (Pedido.EstadoPedido e : Pedido.EstadoPedido.values()) {
            conteoPorEstado.put(e, 0);
        }
        Map<String, Double> ingresosPorTipo = new LinkedHashMap<>();
        ingresosPorTipo.put("Bici", 0.0);
        ingresosPorTipo.put("Moto", 0.0);
        ingresosPorTipo.put("Carro", 0.0);
        ingresosPorTipo.put("Dron", 0.0);

        double ingresosTotales = 0;
        for (Pedido p : pedidos) {
            conteoPorEstado.put(p.getEstado(), conteoPorEstado.get(p.getEstado()) + 1);
            if (p.getEstado() == Pedido.EstadoPedido.ENTREGADO) {
                ingresosTotales += p.getCosto();
                String tipo = p.getRepartidorAsignado().getTipo();
                ingresosPorTipo.put(tipo, ingresosPorTipo.getOrDefault(tipo, 0.0) + p.getCosto());
            }
        }

        // Repartidor estrella: mejor promedio entre los que tienen calificaciones
        Repartidor estrella = null;
        for (Repartidor r : repartidores) {
            if (r.tieneCalificaciones() && (estrella == null || r.obtenerPromedio() > estrella.obtenerPromedio())) {
                estrella = r;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n====== CIERRE DEL DÍA ======\n");
        sb.append("Total de pedidos: ").append(pedidos.size()).append("\n");
        for (Map.Entry<Pedido.EstadoPedido, Integer> e : conteoPorEstado.entrySet()) {
            sb.append("  ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        sb.append("Ingresos del día (solo ENTREGADOS): ").append(dinero(ingresosTotales)).append("\n");
        sb.append("Ingresos por tipo de repartidor:\n");
        for (Map.Entry<String, Double> e : ingresosPorTipo.entrySet()) {
            sb.append("  ").append(e.getKey()).append(": ").append(dinero(e.getValue())).append("\n");
        }
        if (estrella == null) {
            sb.append("Repartidor estrella: aún no hay calificaciones\n");
        } else {
            sb.append("Repartidor estrella: ").append(estrella.getNombre()).append(" (").append(estrella.getTipo())
              .append(") con promedio ").append(String.format("%.2f", estrella.obtenerPromedio())).append("\n");
        }
        return sb.toString();
    }

    private String dinero(double valor) {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,.0f", valor);
    }




}
