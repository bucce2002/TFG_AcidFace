package jesus.com.mylogin.acid_face_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProd extends RecyclerView.Adapter<AdapterProd.ViewHolderProd> {

    private Context context;
    private ArrayList<Ropa> listaProd;

    public AdapterProd(Context context, ArrayList<Ropa> listaProd) {
        this.context = context;
        this.listaProd = listaProd;
    }

    @NonNull
    @Override
    public ViewHolderProd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.producto,
                parent,
                false);
        return new ViewHolderProd(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProd holder, int position) {

        Ropa ropa = listaProd.get(position);
        holder.txtNombre.setText(ropa.getNombre());
        holder.txtTallas.setText(ropa.getTalla());
        holder.txtPrecio.setText(String.valueOf(ropa.getPrecio()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetallesActivity.class);
                intent.putExtra("detalle", String.valueOf(listaProd.get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProd.size();
    }

    public static class ViewHolderProd extends RecyclerView.ViewHolder{

        private ImageView imgProd;
        private TextView txtNombre, txtPrecio, txtTallas;
        private Button btCarrito;

        public ViewHolderProd(@NonNull View itemView) {
            super(itemView);

            imgProd = itemView.findViewById(R.id.imgProd);
            txtNombre = itemView.findViewById(R.id.nomProd);
            txtTallas = itemView.findViewById(R.id.tallaProd);
            txtPrecio = itemView.findViewById(R.id.precioProd);
        }
    }
}
