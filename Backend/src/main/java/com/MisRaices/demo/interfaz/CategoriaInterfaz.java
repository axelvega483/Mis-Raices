package com.MisRaices.demo.interfaz;

import com.MisRaices.demo.entity.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaInterfaz {

    public Categoria guardar(Categoria categoria);

    public void eliminar(Integer id);

    public Optional<Categoria> obtener(Integer id);

    public List<Categoria> listar();
}
