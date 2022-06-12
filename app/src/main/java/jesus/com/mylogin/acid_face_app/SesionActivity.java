package jesus.com.mylogin.acid_face_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SesionActivity extends AppCompatActivity {
    private TextView btnGoogle,btnFace,registro, sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_sesion);
        btnFace = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);
        registro = findViewById(R.id.btnRegistro2);
        sesion = findViewById(R.id.btnIniciaSesion);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SesionActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SesionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}