package com.example.misraices.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misraices.R;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.ItemTarjetaBinding;

import java.util.List;

public class AdapterTarjetaCompra extends RecyclerView.Adapter<AdapterTarjetaCompra.ViewHolder> {
    private List<TarjetaCredito> listaTarjetas;
    private Context context;
    private int tarjetaSeleccionadaPos = -1;
    private OnTarjetaSeleccionadaListener listener;

    public interface OnTarjetaSeleccionadaListener {
        void onTarjetaSeleccionada(TarjetaCredito tarjeta);
    }

    public AdapterTarjetaCompra(List<TarjetaCredito> listaTarjetas, Context context, OnTarjetaSeleccionadaListener listener) {
        this.listaTarjetas = listaTarjetas;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterTarjetaCompra.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemTarjetaBinding binding = ItemTarjetaBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTarjetaCompra.ViewHolder holder, int position) {
        TarjetaCredito tarjeta = listaTarjetas.get(position);


        holder.binding.NumeroTxt.setText("N°: " + tarjeta.getNumero());
        holder.binding.TitularTxt.setText("Titular: " + tarjeta.getTitular());
        holder.binding.VencimientoTxt.setText("Venc: " + tarjeta.getFechaVencimiento());
        holder.binding.TipoTxt.setText("Tipo: " + tarjeta.getTipo());
        holder.binding.CodigoTxt.setText("Codigo: " + tarjeta.getCodigoSeguridad());

        if (position == tarjetaSeleccionadaPos) {
            holder.binding.getRoot().findViewById(R.id.constraintLayoutTarjeta).setBackgroundResource(R.drawable.bg_tarjeta_seleccionada);

        } else {
            holder.binding.getRoot().findViewById(R.id.constraintLayoutTarjeta).setBackgroundResource(R.drawable.bg_tarjeta_normal);

        }

        holder.itemView.setOnClickListener(v -> {
            tarjetaSeleccionadaPos = position;
            listener.onTarjetaSeleccionada(tarjeta);
            notifyDataSetChanged();
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
