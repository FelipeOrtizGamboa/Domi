package modelo;

public class Dron extends Repartidor {

    public Dron(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public double calcularCosto(double km) {
        return 4000 + km * 600;
    }

    @Override
    public double calcularTiempo(double km) {
        return km / 50 * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean grande, boolean lluvia) {
        return km <= 8 && !grande && !lluvia;
    }

    @Override
    public String getTipo() {
        return "Dron";
    }
}
