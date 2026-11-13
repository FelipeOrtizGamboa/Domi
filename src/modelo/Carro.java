package modelo;

public class Carro extends Repartidor {
    // Datos del carro
    private static final double TARIFA_BASE = 5000;
    private static final double VALOR_POR_KM = 1200;
    private static final double VELOCIDAD_KMH = 25;

    // Constructor. info repartidor
    public Carro(String codigo, String nombre, boolean disponible) {
        super(codigo, nombre, disponible);
    }

    @Override
    public double calcularCosto(double km) {
        // Costo del domicilio = tarifa base + (kilómetros × valor por km)
        return TARIFA_BASE + (km * VALOR_POR_KM);
    }

    @Override
    public double calcularTiempo(double km) {
        // Tiempo estimado (minutos) = kilómetros ÷ velocidad × 60
        return km / VELOCIDAD_KMH * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean esGrande, boolean lluvia) {
        // Sin límite de distancia, sí lleva pedidos grandes y sí puede llevar pedidos con lluvia
        return true;
    }
}
