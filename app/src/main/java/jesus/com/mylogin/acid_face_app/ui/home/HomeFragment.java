package jesus.com.mylogin.acid_face_app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import jesus.com.mylogin.acid_face_app.CarritoActivity;
import jesus.com.mylogin.acid_face_app.LoginActivity;
import jesus.com.mylogin.acid_face_app.ProductosActivity;
import jesus.com.mylogin.acid_face_app.R;
import jesus.com.mylogin.acid_face_app.databinding.FragmentHomeBinding;
import jesus.com.mylogin.acid_face_app.ui.ayuda.AyudaActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button btEstrenos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btEstrenos = root.findViewById(R.id.btEstrenos);
        btEstrenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductosActivity.class));
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