package modelo;

public class Carro extends Repartidor {
    // Datos del carro
    private static final double TARIFA_BASE = 5000;
    private static final double VALOR_POR_KM = 1200;
    private static final double VELOCIDAD_KMH = 25;

    // Constructor. info repartidor
    public Carro(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public double calcularCosto(double km) {
        // Costo del domicilio = tarifa base + (kilómetros × valor por km)
        validarKm(km);
        return TARIFA_BASE + (km * VALOR_POR_KM);
    }

    @Override
    public double calcularTiempo(double km) {
        // Tiempo estimado (minutos) = kilómetros ÷ velocidad × 60
        validarKm(km);
        return km / VELOCIDAD_KMH * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean esGrande) {
        // Sin límite de distancia y sí lleva pedidos grandes
        return true;
    }

    // La clase se valida a sí misma. los km deben ser ingresados correctamente, para evitar error en operaciones
    private void validarKm(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("Los kilómetros no pueden ser negativos");
        }
    }
}
