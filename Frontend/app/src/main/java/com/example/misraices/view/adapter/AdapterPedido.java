package com.example.misraices.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misraices.data.model.Pedido;
import com.example.misraices.databinding.ItemPedidosBinding;

import java.util.List;

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.ViewHolder> {
    private List<Pedido> listaPedidos;
    private Context context;
    private OnPedidoClickListener listener;

    public interface OnPedidoClickListener {
        void onPedidoClick(Pedido pedido);
    }

    public AdapterPedido(List<Pedido> listaPedidos, Context context, OnPedidoClickListener listener) {
        this.listaPedidos = listaPedidos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterPedido.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemPedidosBinding binding = ItemPedidosBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPedido.ViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);
        holder.binding.OrdenTxt.setText("Orden: " + pedido.getId());
        holder.binding.TotalTxt.setText("Total: " + pedido.getTotal());
        holder.binding.EstadoTxt.setText("Estado: " + pedido.getEstado());
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onPedidoClick(pedido);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPedidosBinding binding;

        public ViewHolder(ItemPedidosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
