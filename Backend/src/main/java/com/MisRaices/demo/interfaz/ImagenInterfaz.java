package com.MisRaices.demo.interfaz;

import com.MisRaices.demo.entity.Imagen;
import java.util.List;
import java.util.Optional;

public interface ImagenInterfaz {

    public Imagen guardar(Imagen imagen);

    public void eliminar(Integer id);

    public Optional<Imagen> obtener(Integer id);

    public List<Imagen> listar();
}
