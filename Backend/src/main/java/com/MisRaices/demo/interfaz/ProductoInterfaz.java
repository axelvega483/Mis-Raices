package com.MisRaices.demo.interfaz;

import com.MisRaices.demo.entity.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoInterfaz {

    public Producto guardar(Producto producto);

    public void eliminar(Integer id);

    public Optional<Producto> obtener(Integer id);

    public List<Producto> listar();
}
