import modelo.Cliente;

public class Pedido {

    public enum EstadoPedido {
        EN_CAMINO,
        ENTREGADO,
        CANCELADO
    }

    private static int siguienteNumero = 1;
    private int numero;
    private Cliente cliente;
    private String descripcion;
    private boolean esGrande;
    private double km;
    private Repartidor repartidorAsignado;
    private long costo;
    private double tiempo;
    private EstadoPedido estado;

    public Pedido(Cliente cliente, String descripcion, boolean esGrande, double km,
                  Repartidor repartidorAsignado, long costo, double tiempo, EstadoPedido estado) {
        if (cliente == null) {
            throw new IllegalArgumentException("El pedido debe tener info del cliente.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("El pedido debe de contener una descripción");
        }
        if (km <= 0) {
            throw  new IllegalArgumentException("La distancia debe debe ser mayor a 0km");
        }
        if (repartidorAsignado == null) {
            throw  new IllegalArgumentException("El pedido debe tener la info del repartidor asignado");
        }
        if (costo < 0) {
            throw  new IllegalArgumentException("El costo debe ser igual o mayor a 0");
        }
        if (tiempo <= 0) {
            throw  new IllegalArgumentException("El tiempo debe ser mayor a 0 minutos");
        }
        if (estado == null) {
            throw  new IllegalArgumentException("El Pedido debe tener un estado válido");
        }

        this.numero = siguienteNumero;
        siguienteNumero ++;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.esGrande = esGrande;
        this.km = km;
        this.repartidorAsignado = repartidorAsignado;
        this.costo = costo;
        this.tiempo = tiempo;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEsGrande() {
        return esGrande;
    }

    public double getKm() {
        return km;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public long getCosto() {
        return costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
