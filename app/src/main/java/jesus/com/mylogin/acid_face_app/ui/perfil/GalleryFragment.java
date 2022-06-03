package jesus.com.mylogin.acid_face_app.ui.perfil;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import jesus.com.mylogin.acid_face_app.Model;
import jesus.com.mylogin.acid_face_app.ui.ayuda.AyudaActivity;
import jesus.com.mylogin.acid_face_app.CarritoActivity;
import jesus.com.mylogin.acid_face_app.LoginActivity;
import jesus.com.mylogin.acid_face_app.R;
import jesus.com.mylogin.acid_face_app.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private TextView txtNombre,txtCarrito,txtPedidios,txtSalir,txtDeseos;
    private FirebaseAuth Fauth;
    private GoogleSignInClient mgoogle;
    private GoogleSignInOptions gso;
    private ImageView foto;
    private FirebaseStorage store;
    private FirebaseDatabase database;
    private StorageReference stReference;
    private DatabaseReference baseDatos10;

    private String RutaAlmacena = "foto";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Fauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        store = FirebaseStorage.getInstance();

        FirebaseUser Fuser = Fauth.getCurrentUser();
        baseDatos10 = FirebaseDatabase.getInstance().getReference("Usuarios");
        baseDatos10.child(Fuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //si el usuario existe
                if (snapshot.exists()){
                    String nombre = ""+snapshot.child("name").getValue();
                    String url = ""+snapshot.child("imagen").getValue();

                    txtNombre.setText(nombre);
                    try {
                        Picasso.get().load(url).into(foto);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.acid1).into(foto);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogle = GoogleSignIn.getClient(getActivity(), gso);


        txtNombre = root.findViewById(R.id.txtNombre);
        foto = root.findViewById(R.id.foto);
        txtPedidios = root.findViewById(R.id.txtpedidos);
        txtCarrito = root.findViewById(R.id.txtCarrito);
        txtDeseos = root.findViewById(R.id.txtDeseos);
        txtSalir = root.findViewById(R.id.txtSalir);

        txtNombre.setText(Fuser.getDisplayName());

        database.getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Model userModel = snapshot.getValue(Model.class);
                        Glide.with(getContext()).load(userModel.getImageUrl()).into(foto);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        txtPedidios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CarritoActivity.class));
            }
        });
        txtCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AyudaActivity.class));
            }
        });
        txtDeseos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AyudaActivity.class));
            }
        });
        txtSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  update();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

              //  actualizarBase(imageUri);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void salir(){
        Fauth.signOut();
        mgoogle.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

      @Override
      public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);

          if (data.getData() != null){
            Uri imageUri = data.getData();
              foto.setImageURI(imageUri);
              final StorageReference reference = store.getReference().child("imgUsuarios").child(FirebaseAuth.getInstance().getUid());
              reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      Toast.makeText(getContext(), "Todo ha ido bien", Toast.LENGTH_SHORT).show();
                      reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              database.getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid())
                                      .child("imagen").setValue(uri.toString());
                          }
                      });
                  }
              });
          }
     }


}