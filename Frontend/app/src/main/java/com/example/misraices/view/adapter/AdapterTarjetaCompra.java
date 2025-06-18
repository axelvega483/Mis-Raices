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

    public AdapterTarjetaCompra(List<TarjetaCredito> listaTarjetas, Context context, OnTarjetaSeleccionadaListener listener,int idUltimaTarjetaUsada) {
        this.listaTarjetas = listaTarjetas;
        this.context = context;
        this.listener = listener;
        // Buscar la posición de la última tarjeta usada
        for (int i = 0; i < listaTarjetas.size(); i++) {
            if (listaTarjetas.get(i).getId() == idUltimaTarjetaUsada) {
                tarjetaSeleccionadaPos = i;
                listener.onTarjetaSeleccionada(listaTarjetas.get(i));
                break;
            }
        }

        // Si no se encontró, seleccionar la primera
        if (tarjetaSeleccionadaPos == -1 && !listaTarjetas.isEmpty()) {
            tarjetaSeleccionadaPos = 0;
            listener.onTarjetaSeleccionada(listaTarjetas.get(0));
        }
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
        holder.binding.getRoot().findViewById(R.id.constraintLayoutTarjeta).setBackgroundResource(R.drawable.bg_tarjeta_seleccionada);

        // Fondo según selección
        int fondo = (position == tarjetaSeleccionadaPos)
                ? R.drawable.bg_tarjeta_seleccionada
                : R.drawable.bg_tarjeta_normal;
        holder.binding.getRoot().findViewById(R.id.constraintLayoutTarjeta).setBackgroundResource(fondo);



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
