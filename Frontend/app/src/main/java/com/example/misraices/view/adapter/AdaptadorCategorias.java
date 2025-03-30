package com.example.misraices.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.misraices.data.model.Categoria;
import com.example.misraices.databinding.ItemCategoriaBinding;

import java.util.List;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {
    private List<Categoria> categorias;
    private Context context;
    private OnCategoriaClickListener categoriaClickListener;

    public AdaptadorCategorias(List<Categoria> categorias, Context context, OnCategoriaClickListener listener) {
        this.categorias = categorias;
        this.context = context;
        this.categoriaClickListener = listener;
    }

    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemCategoriaBinding binding = ItemCategoriaBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.binding.nombreCategoriaTxt.setText(categoria.getNombre());

        Glide.with(context)
                .load(categoria.getImg())
                .circleCrop()
                .into(holder.binding.imgCategoria);

        holder.itemView.setOnClickListener(v -> {
            if (categoriaClickListener != null) {
                categoriaClickListener.onCategoriaClick(categoria);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoriaBinding binding;

        public ViewHolder(ItemCategoriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
