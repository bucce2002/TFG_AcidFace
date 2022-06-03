package jesus.com.mylogin.acid_face_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




public class ProductosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference database;
    private AdapterProd adapterProd;
    private ArrayList<Ropa> listaRopas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        recyclerView = findViewById(R.id.RecyProd);
        database = FirebaseDatabase.getInstance().getReference("Ropa").child("camisetas");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaRopas = new ArrayList<>();
        adapterProd = new AdapterProd(this, listaRopas);
        recyclerView.setAdapter(adapterProd);

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