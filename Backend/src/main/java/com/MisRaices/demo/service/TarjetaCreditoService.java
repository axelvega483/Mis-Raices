package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.TarjetaCredito;
import com.MisRaices.demo.interfaz.TarjetaCreditoInterfaz;
import com.MisRaices.demo.repository.TarjetaCreditoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaCreditoService implements TarjetaCreditoInterfaz {

    @Autowired
    private TarjetaCreditoRepository repo;

    @Override
    public TarjetaCredito guardar(TarjetaCredito tarjetaCredito) {
        return repo.save(tarjetaCredito);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<TarjetaCredito> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<TarjetaCredito> listar() {
        return repo.findAll();
    }

}
