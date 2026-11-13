package modelo;

public class Moto extends Repartidor {

    public Moto(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public double calcularCosto(double km) {
        return 3000 + km * 1000;
    }

    @Override
    public double calcularTiempo(double km) {
        return km / 30 * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean grande, boolean lluvia) {
        return km <= 15 && !grande;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
}
