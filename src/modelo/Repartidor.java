package modelo;
import java.util.ArrayList;
import java.util.List;

public abstract class Repartidor implements Calificable {

    private String codigo;
    private String nombre;
    private boolean disponible;
    private List<Double> calificaciones = new ArrayList<>();


    public Repartidor (String codigo, String nombre, boolean disponible) {
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El repartidor necesita un código");
        }
        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El repartidor necesita un nombre");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.disponible = disponible;
    }

    public String getCodigo(){
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public abstract double calcularCosto(double km);

    public abstract double calcularTiempo(double km);

    public abstract boolean puedeLlevar(double km, boolean esGrande);

    @Override
    public void recibirCalificacion(double nota) {
        if (nota < 1.0 || nota > 5.0) {
            throw new IllegalArgumentException("La calificación debe estar entre 1.0 y 5.0");
        }
        this.calificaciones.add(nota);
    }

    @Override
    public double calcularPromedio() {
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        double suma = 0;
        for (double nota : calificaciones) {
            suma += nota;
        }
        return suma / calificaciones.size();
    }
}
