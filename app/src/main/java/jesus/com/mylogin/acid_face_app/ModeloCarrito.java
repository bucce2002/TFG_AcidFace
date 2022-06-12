package jesus.com.mylogin.acid_face_app;

import java.io.Serializable;

public class ModeloCarrito implements Serializable {

    String nombreProducto, precioProducto, fechaActual, horaActual, cantidad, talla,dc;
    int precioTotal;

    public ModeloCarrito() {
    }

    public ModeloCarrito(String nombreProducto, String precioProducto, String fechaActual, String horaActual, String cantidad, String talla, String dc, int precioTotal) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.fechaActual = fechaActual;
        this.horaActual = horaActual;
        this.cantidad = cantidad;
        this.talla = talla;
        this.dc = dc;
        this.precioTotal = precioTotal;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getPrecioProducto() {
        return precioProducto;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public String getHoraActual() {
        return horaActual;
    }

    public String getTalla() {
        return talla;
    }

    public String getCantidad() {
        return cantidad;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }
}
