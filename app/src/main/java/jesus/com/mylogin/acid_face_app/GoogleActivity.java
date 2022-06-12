package jesus.com.mylogin.acid_face_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import jesus.com.mylogin.acid_face_app.ui.home.HomeFragment;
import jesus.com.mylogin.acid_face_app.ui.perfil.GalleryFragment;

public class GoogleActivity extends LoginActivity {

    private static final int RC_SIGN_IN = 110;
    private FirebaseAuth Fauth1;
    private FirebaseUser FUser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSign = GoogleSignIn.getClient(this, gso);

        Fauth1 = FirebaseAuth.getInstance();
        FUser1 = Fauth1.getCurrentUser();

        Intent signIntent = googleSign.getSignInIntent();
        startActivityForResult(signIntent,RC_SIGN_IN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()){
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                }catch (ApiException e){
                    Toast.makeText(this, "Ha ocurrido un error"+task.getException(), Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        }
    }
    public void firebaseAuthWithGoogle(String idToken){
        AuthCredential credencial = GoogleAuthProvider.getCredential(idToken,null);
        Fauth1.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = Fauth1.getCurrentUser();
                    updateUI(user);
                }else{
                    finish();
                    Toast.makeText(GoogleActivity.this, "Ha ocurrido un error"+task.getException(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(GoogleActivity.this, PrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}