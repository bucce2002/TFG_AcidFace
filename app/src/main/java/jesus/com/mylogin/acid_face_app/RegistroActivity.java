package jesus.com.mylogin.acid_face_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private EditText correo,clave,Rclave,Usuario,Tdireccion;
    private TextView btnRegistrarse,btnIrRegistro;
    private String email = "",contra1= "",contra2="",nombre="",direccion="";
    FirebaseAuth FAuth;
    FirebaseAuth.AuthStateListener AuthListener;
    DatabaseReference mdatabase;

    public static final int REQUEST_CODE = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        correo = findViewById(R.id.editCorreo);
        clave = findViewById(R.id.editClave);
        Rclave = findViewById(R.id.editRclave);
        Usuario = findViewById(R.id.editUsuario);
        btnRegistrarse = findViewById(R.id.txtRegistro2);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);
        Tdireccion = findViewById(R.id.txtdireccion);
        FAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });
        btnIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( RegistroActivity.this,LoginActivity.class));
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


    }


    @Override
    protected void onStart() {
        super.onStart();
        FAuth.addAuthStateListener(AuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (AuthListener!=null){
            FAuth.removeAuthStateListener(AuthListener);
        }
    }

    //a la hora de presentar hay que modificar los toast.AL CLIEWNTE SE LA CHUPA
    public void crearUsuario(){
        nombre = Usuario.getText().toString().trim();
        email = correo.getText().toString().trim();
        contra1 = clave.getText().toString().trim();
        contra2 = Rclave.getText().toString().trim();
        direccion = Tdireccion.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            correo.setError("El correo no se puede dejar en blanco");
            correo.requestFocus();
        } else if (TextUtils.isEmpty(nombre)){
            Usuario.setError("La clave no se puede dejar en blanco");
            Usuario.requestFocus();
        }else if (TextUtils.isEmpty(contra1)){
            clave.setError("La clave no se puede dejar en blanco");
            clave.requestFocus();
        }else if (TextUtils.isEmpty(contra2)){
            Rclave.setError("La clave no se puede dejar en blanco");
            Rclave.requestFocus();
        }else if (TextUtils.isEmpty(direccion)){
            Rclave.setError("La clave no se puede dejar en blanco");
            Rclave.requestFocus();
        }else{
            crearDatos();
        }


    }
    public void crearDatos(){
        FAuth.createUserWithEmailAndPassword(email,contra1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",nombre);
                    map.put("email",email);
                    map.put("clave",contra1);
                    map.put("direccion",direccion);
                    map.put("puntos","0");

                    String id =FAuth.getCurrentUser().getUid();
                    mdatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            startActivity(new Intent(RegistroActivity.this,LoginActivity.class));
                        }
                    });
                    Toast.makeText(RegistroActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroActivity.this, PrincipalActivity.class));
                }else{
                    Toast.makeText(RegistroActivity.this, "Ha ocurrido un error, "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}