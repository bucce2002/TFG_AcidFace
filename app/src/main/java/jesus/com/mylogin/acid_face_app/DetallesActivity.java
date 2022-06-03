package jesus.com.mylogin.acid_face_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetallesActivity extends AppCompatActivity {

    ImageView imagen_detallada, añadir_item, remover_item;
    TextView precio, descripcion;
    Button añadir_carrito;
    Ropa ropa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        final Object object = getIntent().getSerializableExtra("detalle");
        if(object instanceof Ropa){
            ropa = (Ropa) object;
        }

        imagen_detallada=findViewById(R.id.imagen_detallada);
        añadir_item=findViewById(R.id.agregar);
        remover_item=findViewById(R.id.remover);
        precio=findViewById(R.id.price);
        descripcion=findViewById(R.id.desc);
        añadir_carrito=findViewById(R.id.btnCarrito);


    }
}