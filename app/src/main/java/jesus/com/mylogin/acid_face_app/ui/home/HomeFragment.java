package jesus.com.mylogin.acid_face_app.ui.home;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import jesus.com.mylogin.acid_face_app.ProductosActivity;
import jesus.com.mylogin.acid_face_app.R;
import jesus.com.mylogin.acid_face_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button btEstrenos, btCamisetas, btSudaderas, btOfertas, btPantalones;
    private ScrollView sv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Intent intent = new Intent(getActivity(), ProductosActivity.class);
        btEstrenos = root.findViewById(R.id.btEstrenos);
        btCamisetas = root.findViewById(R.id.btCamisetas);
        btSudaderas = root.findViewById(R.id.btSudaderas);
        sv = root.findViewById(R.id.scrollProd);
        btPantalones = root.findViewById(R.id.btPantalones);
        btOfertas = root.findViewById(R.id.btOfertas);

        //ANIMACION DEL FONDO
        AnimationDrawable animationDrawable =  (AnimationDrawable) sv.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        btEstrenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("categoria","estrenos");
                startActivity(intent);
            }
        });

        btCamisetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("categoria","camisetas");
                startActivity(intent);
            }
        });

        btSudaderas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("categoria","sudaderas");
                startActivity(intent);
            }
        });
        btPantalones.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                intent.putExtra("categoria","banadores");
                startActivity(intent);
            }
        });
        btOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("categoria","ofertas");
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}