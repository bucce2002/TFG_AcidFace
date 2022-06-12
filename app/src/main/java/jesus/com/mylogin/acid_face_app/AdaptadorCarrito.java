package jesus.com.mylogin.acid_face_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {

    Context context;
    List<ModeloCarrito> carritoModeloLista;
    int precioTotal = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public AdaptadorCarrito(Context context, List<ModeloCarrito> carritoModeloLista) {
        this.context = context;
        this.carritoModeloLista = carritoModeloLista;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public AdaptadorCarrito.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCarrito.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nombre.setText(carritoModeloLista.get(position).getNombreProducto());
        holder.precio.setText(carritoModeloLista.get(position).getPrecioProducto());
        holder.cantidad.setText(carritoModeloLista.get(position).getCantidad());
        holder.fecha.setText(carritoModeloLista.get(position).getFechaActual());
        holder.hora.setText(carritoModeloLista.get(position).getHoraActual());
        holder.talla.setText((carritoModeloLista.get(position).getTalla()));
        holder.precioTotal.setText(String.valueOf(carritoModeloLista.get(position).getPrecioTotal()));
        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("UsuarioActual")
                        .document(auth.getCurrentUser().getUid())
                        .collection("añadirCarrito")
                        .document(carritoModeloLista.get(position).getDc())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            carritoModeloLista.remove(carritoModeloLista.get(position));
                            notifyDataSetChanged();
                            Toast.makeText(context, "Este producto ha sido eliminado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        precioTotal = precioTotal + carritoModeloLista.get(position).getPrecioTotal();
        Intent intent = new Intent("PrecioTotal");
        intent.putExtra("precioTotal",precioTotal);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return carritoModeloLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, precio, cantidad, fecha, precioTotal, hora, talla;
        ImageView borrar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.nombre_producto);
            precio=itemView.findViewById(R.id.precio_producto);
            cantidad=itemView.findViewById(R.id.cantidad_carrito);
            fecha=itemView.findViewById(R.id.fecha_actual);
            hora=itemView.findViewById(R.id.hora_actual);
            talla = itemView.findViewById(R.id.cantidad_talla);
            precioTotal=itemView.findViewById(R.id.precio_total);
            borrar = itemView.findViewById(R.id.icDelete);
        }
    }
}
