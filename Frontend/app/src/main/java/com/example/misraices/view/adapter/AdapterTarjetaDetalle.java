package com.example.misraices.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misraices.data.model.Producto;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.ItemTarjetaBinding;

import java.util.List;

public class AdapterTarjetaDetalle extends RecyclerView.Adapter<AdapterTarjetaDetalle.ViewHolder> {
    private List<TarjetaCredito> listaTarjetas;
    private Context context;
    private OnProductoClickListener listener;

    public interface OnProductoClickListener {
        void onProductoClick(TarjetaCredito tarjetaCredito);
    }

    public AdapterTarjetaDetalle(List<TarjetaCredito> listaTarjetas, Context context, OnProductoClickListener listener) {
        this.listaTarjetas = listaTarjetas;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterTarjetaDetalle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemTarjetaBinding binding = ItemTarjetaBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTarjetaDetalle.ViewHolder holder, int position) {
        TarjetaCredito tarjeta = listaTarjetas.get(position);

        // Mostrar datos
        holder.binding.NumeroTxt.setText("Numero: " + tarjeta.getNumero());
        holder.binding.TitularTxt.setText("Titular: " + tarjeta.getTitular());
        holder.binding.VencimientoTxt.setText("Vencimiento: " + tarjeta.getFechaVencimiento());
        holder.binding.TipoTxt.setText("Tipo: " + tarjeta.getTipo());
        holder.binding.CodigoTxt.setText("Codigo: " + tarjeta.getCodigoSeguridad());
        holder.itemView.setOnClickListener(v -> {
            listener.onProductoClick(tarjeta);
        });
    }

    @Override
    public int getItemCount() {
        return listaTarjetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTarjetaBinding binding;

        public ViewHolder(ItemTarjetaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
