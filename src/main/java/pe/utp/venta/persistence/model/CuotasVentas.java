package pe.utp.venta.persistence.model;



public class CuotasVentas {
    private int cuota;
    private String monto;
    private String descripcion;
    private String fecha;

    public CuotasVentas() {
    }

    public CuotasVentas(int cuota, String monto, String descripcion, String fecha) {
        this.cuota = cuota;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}