package Model;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Gasto {
    public static final List<String> TIPOS_VALIDOS = Arrays.asList("gasolina", "revision", "ITV", "cambio de aceite", "otros");

    private String tipo;
    private int kilometraje;
    private Date fecha;
    private double importe;
    private String descripcion;

    public Gasto(String tipo, int kilometraje, Date fecha, double importe, String descripcion) {
        this.tipo = tipo;
        this.kilometraje = kilometraje;
        this.fecha = fecha;
        this.importe = importe;
        this.descripcion = descripcion;
    }

   



	public String getTipo() {
        return tipo;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getImporte() {
        return importe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Gasto{" +
                "tipo='" + tipo + '\'' +
                ", kilometraje=" + kilometraje +
                ", fecha=" + sdf.format(fecha) +
                ", importe=" + importe +
                ", descripcion='" + (descripcion == null ? "" : descripcion) + '\'' +
                '}';
    }









}
