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
import com.google.firebase.auth.FirebaseUser;


 public class LoginActivity extends AppCompatActivity {

    private EditText correo,clave;
    private TextView btnAceder,olvidarClave;
    private Button bt;
    String email,contra;

    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.txtEmail);
        clave = findViewById(R.id.txtContra);
        btnAceder = findViewById(R.id.btnAcceder);
        olvidarClave = findViewById(R.id.txtOlvidar);
        bt = findViewById(R.id.button1);
        FAuth = FirebaseAuth.getInstance();

        btnAceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntrarUsuario();
            }
        });
        olvidarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RecuperarActivity.class));
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
            }
        });
    }


    public void EntrarUsuario(){
        email = correo.getText().toString().trim();
        contra = clave.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            correo.setError("El correo no se puede dejar en blanco");
            correo.requestFocus();
        }else if (TextUtils.isEmpty(contra)){
            clave.setError("La clave no se puede dejar en blanco");
            clave.requestFocus();
        }else{
            FAuth.signInWithEmailAndPassword(email,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Usuario logueado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Ha ocurrido un error, "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}