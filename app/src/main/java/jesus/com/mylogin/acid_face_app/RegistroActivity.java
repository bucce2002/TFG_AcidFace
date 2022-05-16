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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {
    private EditText correo,clave,Rclave;
    private TextView btnRegistrarse;

    private String email,contra1,contra2;
    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        correo = findViewById(R.id.editCorreo);
        clave = findViewById(R.id.editClave);
        Rclave = findViewById(R.id.editRclave);
        btnRegistrarse = findViewById(R.id.txtRegistro2);

        FAuth = FirebaseAuth.getInstance();
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });
    }
    //a la hora de presentar hay que modificar los toast.AL CLIEWNTE SE LA CHUPA
    public void crearUsuario(){
        email = correo.getText().toString().trim();
        contra1 = clave.getText().toString().trim();
        contra2 = Rclave.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            correo.setError("El correo no se puede dejar en blanco");
            correo.requestFocus();
        }else if (TextUtils.isEmpty(contra1)){
            clave.setError("La clave no se puede dejar en blanco");
            clave.requestFocus();
        }else if (TextUtils.isEmpty(contra2)){
            Rclave.setError("La clave no se puede dejar en blanco");
            Rclave.requestFocus();
        }else{
            FAuth.createUserWithEmailAndPassword(email,contra1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistroActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegistroActivity.this, "Ha ocurrido un error, "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}