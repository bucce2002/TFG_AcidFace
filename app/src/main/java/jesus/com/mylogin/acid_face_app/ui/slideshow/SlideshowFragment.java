package jesus.com.mylogin.acid_face_app.ui.slideshow;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jesus.com.mylogin.acid_face_app.AdaptadorCarrito;
import jesus.com.mylogin.acid_face_app.ModeloCarrito;
import jesus.com.mylogin.acid_face_app.PedidosActivity;
import jesus.com.mylogin.acid_face_app.R;

public class SlideshowFragment extends Fragment {

    RecyclerView recyclerView;
    AdaptadorCarrito adaptadorCarrito;
    List<ModeloCarrito> modeloCarritoList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextView precioTotal,txtInfo;
    int cuentaTotal;
    EditText direccioon,telefon;
    Button comprar;
    AlertDialog dialog;
    TextView segir;


    public SlideshowFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        precioTotal = root.findViewById(R.id.carrito_total);
        comprar=root.findViewById(R.id.btnPagar);


        AlertDialog.Builder build = new AlertDialog.Builder(getContext());
        build.setTitle("Datos adicionales");
        View view = getLayoutInflater().inflate(R.layout.obtenerloca,null);
        direccioon = view.findViewById(R.id.direccion);
        telefon = view.findViewById(R.id.telefon);
        txtInfo = view.findViewById(R.id.txtInfo);
        segir = view.findViewById(R.id.btnContinuar);

        segir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(direccioon.getText())) {
                    direccioon.setError("Campo obligatorio");
                    direccioon.requestFocus();
                }else{
                dialog.dismiss();
                enviar();
            }}
        });
        build.setView(view);
        dialog = build.create();



        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(Mensaje,new IntentFilter("PrecioTotal"));
        recyclerView = root.findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        modeloCarritoList = new ArrayList<>();
        adaptadorCarrito = new AdaptadorCarrito(getActivity(), modeloCarritoList);
        recyclerView.setAdapter(adaptadorCarrito);


        firestore.collection("UsuarioActual").document(auth.getCurrentUser().getUid())
                .collection("añadirCarrito").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                                String dc= documentSnapshot.getId();

                                ModeloCarrito modeloCarrito = documentSnapshot.toObject(ModeloCarrito.class);
                                modeloCarrito.setDc(dc);
                                modeloCarritoList.add(modeloCarrito);
                                adaptadorCarrito.notifyDataSetChanged();
                            }
                        }
                    }
                });

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.show();
            }
        });

        return root;
    }

    public BroadcastReceiver Mensaje = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int total = intent.getIntExtra("precioTotal",0);
            precioTotal.setText("Precio total: " + total+"€");
        }
    };


    public void enviar(){
         String str = cambiarFormatoLista();
         String DireccionS="AcidFaceBs@gmail.com",concepto="Pedido",Sdirec = direccioon.getText().toString().trim(),STelef = telefon.getText().toString().trim();
         Intent intent = new Intent(Intent.ACTION_SEND);
         intent.setData(Uri.parse("mailto:"));
         intent.setType("text/plain");
         intent.putExtra(Intent.EXTRA_EMAIL,new String[]{DireccionS});
         intent.putExtra(Intent.EXTRA_SUBJECT,concepto);
         intent.putExtra(Intent.EXTRA_TEXT,"Gracia por comprar en AcidFace Company, estamos tramitando su pedido,\n WELCOME TO THE NEW ERA \n Direccion: "+Sdirec+"\n"+"Telefono :"+STelef+"\n" +"PEDIDO :"+"\n"+str);
         startActivity(Intent.createChooser(intent,"Mensaje enviado perfect"));
    }

    public String cambiarFormatoLista(){
        String lista= null;
        for (ModeloCarrito item:modeloCarritoList) {
            lista+=(item.getNombreProducto()+"\n");
            lista+=(item.getFechaActual()+"\n");
            lista+=(item.getPrecioProducto()+"\n");
        }
        return lista;
    }





    }
