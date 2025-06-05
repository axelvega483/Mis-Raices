package com.example.misraices.viewModel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.Result;
import com.example.misraices.data.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PedidoViewModel extends ViewModel {
    private final PedidoRepository repo = new PedidoRepository();
    private final MutableLiveData<Pedido> pedidoMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<PedidoDetalle>> detallesLiveData = new MutableLiveData<>(new ArrayList<>());
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Context context;

    public void setPedidoMutableLiveData(Pedido pedidos) {
        executor.execute(() -> pedidoMutableLiveData.postValue(pedidos));
    }


    public void init(Context context) {
        this.context = context.getApplicationContext(); // para evitar perdida de memoria
        cargarCarritoDesdePrefs();
    }


    public void cargarPedidosDetalles(PedidoDetalle detalle) {
        List<PedidoDetalle> listaActual = detallesLiveData.getValue();
        if (listaActual != null) {
            listaActual.add(detalle);
            detallesLiveData.postValue(listaActual);
            guardarCarritoEnPrefs();
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        List<PedidoDetalle> detalles = detallesLiveData.getValue();
        if (detalles != null) {
            for (PedidoDetalle detalle : detalles) {
                int cantidad = (detalle.getCantidad() != null) ? detalle.getCantidad() : 1;
                total += detalle.getProducto().getPrecio() * cantidad;
            }
        }
        return total;
    }


    public void actualizarListaPedidos(List<PedidoDetalle> nuevaLista) {
        detallesLiveData.setValue(nuevaLista);
        guardarCarritoEnPrefs();
    }

    public boolean existeProductoEnCarrito(int idProducto) {
        for (PedidoDetalle detalle : detallesLiveData.getValue()) {
            if (detalle.getProducto().getId() == idProducto) {
                return true;
            }
        }
        return false;
    }

    public MutableLiveData<Result<Pedido>> obtenerPedidoPorId(int id) {
        return repo.obtenerPorId(id);
    }


    public MutableLiveData<List<PedidoDetalle>> getDetallesLiveData() {
        return detallesLiveData;
    }

    public MutableLiveData<Result<Pedido>> crearPedido(Pedido pedido) {
        return repo.crear(pedido);
    }

    public MutableLiveData<List<Pedido>> obtenerPedidos() {
        return repo.obtener();
    }

    public MutableLiveData<Result<Pedido>> finalizarCompra(int pedidoId, int tarjetaId) {
        return repo.finalizarCompra(pedidoId, tarjetaId);
    }

    private void guardarCarritoEnPrefs() {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences("mis_raices", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(detallesLiveData.getValue());
        editor.putString("carrito", json);
        editor.apply();
    }

    private void cargarCarritoDesdePrefs() {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences("mis_raices", Context.MODE_PRIVATE);
        String json = prefs.getString("carrito", null);
        if (json != null) {
            Type type = new TypeToken<List<PedidoDetalle>>() {
            }.getType();
            List<PedidoDetalle> lista = new Gson().fromJson(json, type);
            detallesLiveData.setValue(lista);
        }
    }

    public void limpiarCarrito() {
        detallesLiveData.setValue(new ArrayList<>());
        guardarCarritoEnPrefs();
    }

}
