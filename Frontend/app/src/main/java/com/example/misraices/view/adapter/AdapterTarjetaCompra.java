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

    private final List<TarjetaCredito> listaTarjetas;
    private final Context context;
    private int tarjetaSeleccionadaPos = -1;
    private final OnTarjetaSeleccionadaListener listener;

    public interface OnTarjetaSeleccionadaListener {
        void onTarjetaSeleccionada(TarjetaCredito tarjeta);
    }

    public AdapterTarjetaCompra(List<TarjetaCredito> listaTarjetas, Context context, OnTarjetaSeleccionadaListener listener, int idUltimaTarjetaUsada) {
        this.listaTarjetas = listaTarjetas;
        this.context = context;
        this.listener = listener;

        if (idUltimaTarjetaUsada != -1) {
            for (int i = 0; i < listaTarjetas.size(); i++) {
                Integer idTarjeta = listaTarjetas.get(i).getId();
                if (idTarjeta != null && idTarjeta == idUltimaTarjetaUsada) {
                    tarjetaSeleccionadaPos = i;
                    listener.onTarjetaSeleccionada(listaTarjetas.get(i));
                    break;
                }
            }
        }

        if (tarjetaSeleccionadaPos == -1 && !listaTarjetas.isEmpty()) {
            tarjetaSeleccionadaPos = 0;
            listener.onTarjetaSeleccionada(listaTarjetas.get(0));
        }
    }

    @NonNull
    @Override
    public AdapterTarjetaCompra.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTarjetaBinding binding = ItemTarjetaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTarjetaCompra.ViewHolder holder, int position) {
        TarjetaCredito tarjeta = listaTarjetas.get(position);

        holder.binding.NumeroTxt.setText("N°: " + tarjeta.getNumero());
        holder.binding.TitularTxt.setText("Titular: " + tarjeta.getTitular());
        holder.binding.VencimientoTxt.setText("Venc: " + tarjeta.getFechaVencimiento());
        holder.binding.CodigoTxt.setText("Código: " + tarjeta.getCodigoSeguridad());

        String tipo = tarjeta.getTipo();
        if ("Visa".equals(tipo)) {
            holder.binding.logoCard.setImageResource(R.drawable.visa);
        } else if ("MasterCard".equals(tipo)) {
            holder.binding.logoCard.setImageResource(R.drawable.mastercard);
        } else {
            holder.binding.logoCard.setImageResource(0); // Sin logo o logo genérico
        }

        int fondo = (position == tarjetaSeleccionadaPos) ? R.drawable.bg_tarjeta_seleccionada : R.drawable.bg_tarjeta_normal;
        holder.binding.constraintLayoutTarjeta.setBackgroundResource(fondo);

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTarjetaBinding binding;

        public ViewHolder(ItemTarjetaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
