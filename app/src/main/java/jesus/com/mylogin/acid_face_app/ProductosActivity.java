package jesus.com.mylogin.acid_face_app;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jesus.com.mylogin.acid_face_app.ui.ayuda.AyudaActivity;


public class ProductosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference database;
    private AdapterProd adapterProd;
    private ArrayList<Ropa> listaRopas;
    private String tabla;
    private ImageView atras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        RelativeLayout relativeLayout = findViewById(R.id.productLayout);
        recyclerView = findViewById(R.id.RecyProd);
        atras = findViewById(R.id.atras);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tabla = getIntent().getExtras().getString("categoria");
        listaRopas = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("Ropa").child(tabla);
        adapterProd = new AdapterProd(this, listaRopas);
        recyclerView.setAdapter(adapterProd);

        //ANIMACION DEL FONDO
        AnimationDrawable animationDrawable =  (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductosActivity.this, PrincipalActivity.class));
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Ropa ropa = dataSnapshot.getValue(Ropa.class);
                    listaRopas.add(ropa);

                }
                adapterProd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}