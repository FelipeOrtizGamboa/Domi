package modelo;


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
    private double costo;
    private double tiempo;
    private EstadoPedido estado;

    public Pedido(Cliente cliente, String descripcion, boolean esGrande, double km,
                  Repartidor repartidorAsignado, double costo, double tiempo) {
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
        this.numero = siguienteNumero;
        siguienteNumero ++;
        this.cliente = cliente;
        this.descripcion = descripcion.trim();
        this.esGrande = esGrande;
        this.km = km;
        this.repartidorAsignado = repartidorAsignado;
        this.costo = costo;
        this.tiempo = tiempo;
        this.estado = EstadoPedido.EN_CAMINO; // apenas se crea queda EN_CAMINO
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

    public double getCosto() {
        return costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo.");
        }
        if (this.estado == EstadoPedido.ENTREGADO || this.estado == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("No se puede cambiar el estado de un pedido que ya fue " + this.estado);
        }
        this.estado = estado;
    }
}
