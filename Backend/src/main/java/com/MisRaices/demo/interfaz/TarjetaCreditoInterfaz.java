package com.MisRaices.demo.interfaz;

import com.MisRaices.demo.entity.TarjetaCredito;
import java.util.List;
import java.util.Optional;

public interface TarjetaCreditoInterfaz {

    public TarjetaCredito guardar(TarjetaCredito tarjetaCredito);

    public void eliminar(Integer id);

    public Optional<TarjetaCredito> obtener(Integer id);

    public List<TarjetaCredito> listar();
}
