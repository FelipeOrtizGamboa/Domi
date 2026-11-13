public abstract class Repartidor {
    private String codigo;
    private String nombre;
    private boolean disponible;

    public Repartidor (String codigo, String nombre, boolean disponible) {
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El repartidor necesita un código");
        }
        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El repartidor necesita un nombre");
        }
        this.codigo = codigo;
        this.nombre = nombre;
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
}
