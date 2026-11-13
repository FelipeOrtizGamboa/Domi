package modelo;

public class Cotizacion {

    private Repartidor repartidor;
    private double costo;
    private double tiempo;

    public Cotizacion(Repartidor repartidor, double costo, double tiempo) {
        this.repartidor = repartidor;
        this.costo = costo;
        this.tiempo = tiempo;
    }


    public boolean esMejorQue(Cotizacion otra) {
        if (Math.abs(costo - otra.costo) > 0.01) {
            return costo < otra.costo;
        }
        return tiempo < otra.tiempo;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public double getCosto() {
        return costo;
    }

    public double getTiempo() {
        return tiempo;
    }
}
