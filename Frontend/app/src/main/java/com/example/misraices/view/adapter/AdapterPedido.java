package com.example.misraices.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.databinding.ItemPedidoDetalleBinding;
import com.example.misraices.viewModel.PedidoViewModel;

import java.util.List;

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.ViewHolder> {
    private List<PedidoDetalle> pedidoDetalles;
    private Context context;
    private PedidoViewModel pedidoViewModel;

    public AdapterPedido(List<PedidoDetalle> pedidoDetalles, Context context, PedidoViewModel pedidoViewModel) {
        this.pedidoDetalles = pedidoDetalles;
        this.context = context;
        this.pedidoViewModel = pedidoViewModel;
    }

    @NonNull
    @Override
    public AdapterPedido.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemPedidoDetalleBinding binding = ItemPedidoDetalleBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PedidoDetalle pedidoDetalle = pedidoDetalles.get(position);

        if (pedidoDetalle.getCantidad() == null) {
            pedidoDetalle.setCantidad(1);
        }

        holder.binding.NombreTxt.setText(pedidoDetalle.getProducto().getNombre());
        holder.binding.PrecioTxt.setText(String.format("$ %.2f", pedidoDetalle.getProducto().getPrecio()));
        holder.binding.txtCantidad.setText(String.valueOf(pedidoDetalle.getCantidad()));

        Glide.with(holder.binding.imgProducto.getContext())
                .load(pedidoDetalle.getProducto().getImg())
                .into(holder.binding.imgProducto);

        // Aumentar cantidad
        holder.binding.btnSumar.setOnClickListener(v -> {
            pedidoDetalle.setCantidad(pedidoDetalle.getCantidad() + 1);
            holder.binding.txtCantidad.setText(String.valueOf(pedidoDetalle.getCantidad()));

            notifyItemChanged(position);
            pedidoViewModel.actualizarListaPedidos(pedidoDetalles); // 🔹 Actualiza la lista
        });

        // Disminuir cantidad
        holder.binding.btnRestar.setOnClickListener(v -> {
            if (pedidoDetalle.getCantidad() > 1) {
                pedidoDetalle.setCantidad(pedidoDetalle.getCantidad() - 1);
                holder.binding.txtCantidad.setText(String.valueOf(pedidoDetalle.getCantidad()));

                notifyItemChanged(position);
                pedidoViewModel.actualizarListaPedidos(pedidoDetalles);
            } else {
                pedidoDetalles.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, pedidoDetalles.size());

                pedidoViewModel.actualizarListaPedidos(pedidoDetalles);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pedidoDetalles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPedidoDetalleBinding binding;

        public ViewHolder(ItemPedidoDetalleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
