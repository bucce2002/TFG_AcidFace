package jesus.com.mylogin.acid_face_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FAuth = FirebaseAuth.getInstance();
     //   btnLogOut.setOnclickListener(){
     //       FAuth.signOut();
     //      startActivity(new Intent(MainActivity.this,LoginActivity.class));
     //   }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = FAuth.getCurrentUser();
        if (user==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }
}