package jesus.com.mylogin.acid_face_app.ui.perfil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import jesus.com.mylogin.acid_face_app.PedidosActivity;
import jesus.com.mylogin.acid_face_app.ui.ayuda.AyudaActivity;
import jesus.com.mylogin.acid_face_app.LoginActivity;
import jesus.com.mylogin.acid_face_app.R;
import jesus.com.mylogin.acid_face_app.databinding.FragmentGalleryBinding;
import jesus.com.mylogin.acid_face_app.ui.slideshow.SlideshowFragment;


    public class GalleryFragment extends Fragment {

        private FragmentGalleryBinding binding;
        private TextView txtNombre, txtInstagram, txtPedidios, txtSalir, txtDeseos;
        private FirebaseAuth Fauth;
        private GoogleSignInClient mgoogle;
        private GoogleSignInOptions gso;
        private ImageView foto;
        private FirebaseStorage store;
        private FirebaseDatabase database;
        private DatabaseReference baseDatos10;


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
                    if (snapshot.exists()) {
                        String nombre = "" + snapshot.child("name").getValue();
                        String url = "" + snapshot.child("imagen").getValue();
                        txtNombre.setText(nombre);
                        foto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 1);
                            }
                        });
                        try {
                            Glide.with(getContext()).load(url).apply(RequestOptions.circleCropTransform()).into(foto);
                        } catch (Exception e) {
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
            txtInstagram = root.findViewById(R.id.txtIG);
            txtSalir = root.findViewById(R.id.txtSalir);

            txtNombre.setText(Fuser.getDisplayName());
            Glide.with(getActivity()).load(Fuser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(foto);

            txtPedidios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), PedidosActivity.class));
                }
            });
            txtInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://www.instagram.com/acidfacebrand/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.instagram.android");

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //No encontró la aplicación, abre la versión web.
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/acidfacebrand/")));

                    }
                }
            });
            txtSalir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salir();
                }
            });
            return root;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

        public void salir() {
            Fauth.signOut();
            mgoogle.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (data.getData() != null) {
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
