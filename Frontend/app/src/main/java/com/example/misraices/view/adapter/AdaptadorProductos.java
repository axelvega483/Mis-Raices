package com.example.misraices.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.misraices.data.model.Producto;
import com.example.misraices.databinding.ItemProductoBinding;
import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolder> {
    private List<Producto> productos;
    private Context context;

    public AdaptadorProductos(List<Producto> productos, Context context) {
        this.productos = productos;
        this.context=context;
    }

    @NonNull
    @Override
    public AdaptadorProductos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemProductoBinding binding = ItemProductoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.binding.NombreTxt.setText(producto.getNombre());
        holder.binding.DescripcionTxt.setText(producto.getDescripcion());
        holder.binding.PrecioTxt.setText(String.format("$ %.2f", producto.getPrecio()));
        Glide.with(context)
                .load(producto.getImg())
                .into(holder.binding.imgProducto);

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductoBinding binding;



        public ViewHolder(ItemProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
