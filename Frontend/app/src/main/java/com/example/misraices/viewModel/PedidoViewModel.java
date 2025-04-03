package com.example.misraices.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PedidoViewModel extends ViewModel {
    private final PedidoRepository repo = new PedidoRepository();
    private final MutableLiveData<List<Pedido>> pedidoMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<PedidoDetalle>> detallesLiveData = new MutableLiveData<>(new ArrayList<>());
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void setPedidoMutableLiveData(List<Pedido> pedidos) {
        executor.execute(() -> pedidoMutableLiveData.postValue(pedidos));
    }


    public void cargarPedidosDetalles(PedidoDetalle detalle) {
        List<PedidoDetalle> listaActual = detallesLiveData.getValue();
        if (listaActual != null) {
            listaActual.add(detalle);
            detallesLiveData.postValue(listaActual);
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        List<PedidoDetalle> detalles = detallesLiveData.getValue();
        if (detalles != null) {
            for (PedidoDetalle detalle : detalles) {
                int cantidad = (detalle.getCantidad() != null) ? detalle.getCantidad() : 1; // Evita null
                total += detalle.getProducto().getPrecio() * cantidad;
            }
        }
        return total;
    }
    public void actualizarListaPedidos(List<PedidoDetalle> nuevaLista) {
        detallesLiveData.setValue(nuevaLista);
    }

    public boolean existeProductoEnCarrito(int idProducto) {
        for (PedidoDetalle detalle : detallesLiveData.getValue()) {
            if (detalle.getProducto().getId() == idProducto) {
                return true;
            }
        }
        return false;
    }
    public MutableLiveData<List<PedidoDetalle>> getDetallesLiveData() {
        return detallesLiveData;
    }
}
