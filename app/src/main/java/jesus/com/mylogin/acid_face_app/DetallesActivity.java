package jesus.com.mylogin.acid_face_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetallesActivity extends AppCompatActivity {

    ImageView imagen_detallada, añadir_item, remover_item;
    TextView precio, tallaa, nombree, cantidadd;
    Button añadir_carrito;
    Ropa ropa = null;
    int cantidadTotal = 1;
    double precioTotal = 0;
    String talla = "";
    RadioButton s, m, l, xl;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detalle");
        if (object instanceof Ropa) {
            ropa = (Ropa) object;
        }

        cantidadd = findViewById(R.id.cantidad);
        imagen_detallada = findViewById(R.id.imagen_detallada);
        añadir_item = findViewById(R.id.agregar);
        remover_item = findViewById(R.id.remover);
        precio = findViewById(R.id.price);
        nombree = findViewById(R.id.nombre);
        tallaa = findViewById(R.id.talla);
        añadir_carrito = findViewById(R.id.btnPagar);
        s = findViewById(R.id.radioS);
        m = findViewById(R.id.radioM);
        l = findViewById(R.id.radioL);
        xl = findViewById(R.id.radioXL);

        if (ropa != null) {
            Glide.with(getApplicationContext()).load(ropa.getUrl()).into(imagen_detallada);
            precio.setText("" + ropa.getPrecio());
            precioTotal = ropa.getPrecio() * cantidadTotal;
            nombree.setText("Nombre: " + ropa.getNombre());
        }

        añadir_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir();
                Toast.makeText(DetallesActivity.this, "Añadido al carrito", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetallesActivity.this, PrincipalActivity.class));
            }
        });

        añadir_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidadTotal < 10) {
                    cantidadTotal++;
                    cantidadd.setText(String.valueOf(cantidadTotal));
                    precioTotal = ropa.getPrecio() * cantidadTotal;
                }
            }
        });

        remover_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidadTotal > 1) {
                    cantidadTotal--;
                    cantidadd.setText(String.valueOf(cantidadTotal));
                    precioTotal = ropa.getPrecio() * cantidadTotal;
                }
            }
        });
    }

    private void añadir() {
        String fechaActual, horaActual;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat fecha = new SimpleDateFormat("MM dd, yyyy");
        fechaActual = fecha.format(cal.getTime());
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss a");
        horaActual = hora.format(cal.getTime());

        while (true) {
            if (s.isChecked()) {
                talla = "S";
                break;
            }
            if (m.isChecked()) {
                talla = "M";
                break;
            }
            if (l.isChecked()) {
                talla = "L";
                break;
            }
            if (xl.isChecked()) {
                talla = "XL";
                break;
            }
            Toast.makeText(this, "Seleccione una talla por favor.", Toast.LENGTH_SHORT).show();
        }

        final HashMap<String, Object> carrito = new HashMap<>();
        carrito.put("nombreProducto", ropa.getNombre());
        carrito.put("precioProducto", precio.getText().toString());
        carrito.put("fechaActual", fechaActual);
        carrito.put("horaActual", horaActual);
        carrito.put("cantidadTotal", cantidadd.getText().toString());
        carrito.put("Talla", talla);
        carrito.put("precioTotal", precioTotal);

        firestore.collection("UsuarioActual").document(auth.getCurrentUser().getUid())
                .collection("añadirCarrito").add(carrito).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
            }

        });
    }
}