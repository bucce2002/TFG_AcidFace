package jesus.com.mylogin.acid_face_app;

import java.io.Serializable;
import java.net.URL;

public class Ropa implements Serializable {
    private String nombre;
    private Double precio;
    private String talla;
    private URL imagen;

    public Ropa() {
    }

    public Ropa(String nombre, Double precio, String talla, URL imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.talla = talla;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getTalla() {
        return talla;
    }

    public URL getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setImagen(URL imagen) {
        this.imagen = imagen;
    }
}
