package jesus.com.mylogin.acid_face_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PedidosActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    private ImageView atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        firestore = FirebaseFirestore.getInstance();
        atras = findViewById(R.id.atras);
        auth = FirebaseAuth.getInstance();
        List<ModeloCarrito> lista = (ArrayList<ModeloCarrito>) getIntent().getSerializableExtra("items");

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PedidosActivity.this, PrincipalActivity.class));
            }
        });

        if (lista != null && lista.size() > 0){
            for(ModeloCarrito modeloCarrito : lista){
                final HashMap<String,Object> carrito = new HashMap<>();
                carrito.put("nombreProducto", modeloCarrito.getNombreProducto());
                carrito.put("precioProducto", modeloCarrito.getPrecioProducto());
                carrito.put("fechaActual", modeloCarrito.getFechaActual());
                carrito.put("horaActual", modeloCarrito.getHoraActual());
                carrito.put("cantidadTotal", modeloCarrito.getCantidad());
                carrito.put("precioTotal", modeloCarrito.getPrecioTotal());

                firestore.collection("UsuarioActual").document(auth.getCurrentUser().getUid())
                        .collection("Orden").add(carrito).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PedidosActivity.this, "Tu pedido ha sido realizado ", Toast.LENGTH_SHORT).show();

                            }

                        });
            }

        }
    }
}