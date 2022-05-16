package jesus.com.mylogin.acid_face_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarActivity extends AppCompatActivity {
    private Button btn;
    private EditText email;
    FirebaseAuth FAuth;
    private String ema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        btn = findViewById(R.id.btnCambiar);
        email = findViewById(R.id.emailR);

        FAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarClave();
            }
        });
    }
    public void recuperarClave(){
        ema = email.getText().toString();
        FAuth.sendPasswordResetEmail(ema).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RecuperarActivity.this, "El correo ha sido enviado exitosamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RecuperarActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}