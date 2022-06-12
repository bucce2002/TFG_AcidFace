package jesus.com.mylogin.acid_face_app;

import java.io.Serializable;
import java.net.URL;

public class Ropa implements Serializable {
    private String Nombre;
    private Double Precio;
    private String Talla;
    private String url;

    public Ropa() {
    }

    public Ropa(String Nombre, Double Precio, String Talla, String url) {
        this.Nombre = Nombre;
        this.Precio = Precio;
        this.Talla = Talla;
        this.url = url;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getTalla() {
        return Talla;
    }

    public void setTalla(String talla) {
        Talla = talla;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
