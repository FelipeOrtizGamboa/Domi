package modelo;

public class Bici extends Repartidor {
    public Bici(String codigo, String nombre) { super(codigo, nombre); }
    @Override public double calcularCosto(double km) { return 2000 + km * 800; }
    @Override public double calcularTiempo(double km) { return km / 15 * 60; }
    @Override public boolean puedeLlevar(double km, boolean grande, boolean lluvia) { return km <= 5 && !grande && !lluvia; }
    @Override public String getTipo() { return "Bici"; }
}
