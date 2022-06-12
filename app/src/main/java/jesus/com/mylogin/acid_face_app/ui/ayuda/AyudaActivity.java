package jesus.com.mylogin.acid_face_app.ui.ayuda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jesus.com.mylogin.acid_face_app.R;
import jesus.com.mylogin.acid_face_app.databinding.ActivityAyudaBinding;

public class AyudaActivity extends Fragment {

    private ActivityAyudaBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = ActivityAyudaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView txt1 = root.findViewById(R.id.txtNombre);
        TextView txt2 = root.findViewById(R.id.textView4);
        TextView txt3 = root.findViewById(R.id.textView5);
        TextView txt4 =root. findViewById(R.id.NacidPoint);
        TextView txt5 = root.findViewById(R.id.textView8);
        TextView txt6 = root.findViewById(R.id.txtAcid);
        TextView txt7 = root.findViewById(R.id.txtDeseos);
        TextView txt8 = root.findViewById(R.id.textView13);

        txt1.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("¿Que es AcidFace?");
            builder.setMessage("AcidFace es una marca de streetwear que nació de la ilusion de crear no solo una marca sino un estilo de vida, una crew y una forma de ser.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt2.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("¿Tiempo para devolver un producto?");
            builder.setMessage("Desde que realizas un pedido tienes 30 dias para solicitae la devolución o cambio del mismo.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt3.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("¿Los productos tienen garantia?");
            builder.setMessage("Los productos tienen garantía de que son la calidad que se dice de los mismos, si tienes algun problema, defecto o tara comunicanoslo y lo arreglaremos lo antes posible.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt4.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Metodos  de pago");
            builder.setMessage("Los métodos de pagó  son paypal y google pay aunque estamos intentando implementar más de cara al futuro.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt5.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tiempos de entrega aproximados.");
            builder.setMessage("Los tiempos de entrega si hay stock serían de 3-5 dias laborables, sin stock recomendamos reservar el producto para poder adquirirlo nada más lo saquemos de nuevo.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt6.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("¿Que son los Acid points?");
            builder.setMessage("Los acid pointa son un sistema de afiliación que consiste en recuperar parte del dinero gastado en nuestros productos para poder tener obtener rebajas y descuentos en los futuros pedidos.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt7.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("¿Como se ganan?");
            builder.setMessage("Se ganan 100 acidpoints por cada € gastado en nuestra tienda y 1000 acidpoints supondran un descuento de 1€ en tu proximo pedido si decides gastarlo.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        txt8.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Validez");
            builder.setMessage(" Los acidpoints tienen una validez de 180 días para que aunque estes tiempo sin comprar sigas sintiéndote uno más de la acid gang.");
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    }
