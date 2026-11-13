package modelo;

public class Carro extends Repartidor {
    private static final double TARIFA_BASE = 5000;
    private static final double VALOR_POR_KM = 1200;
    private static final double VELOCIDAD_KMH = 25;

    public Carro(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public double calcularCosto(double km) {
        return TARIFA_BASE + (km * VALOR_POR_KM);
    }

    @Override
    public double calcularTiempo(double km) {
        return km / VELOCIDAD_KMH * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean esGrande, boolean lluvia) {
        // Sin límite de distancia, sí lleva pedidos grandes y sale con lluvia
        return true;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }
}
