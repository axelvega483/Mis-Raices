package com.MisRaices.ProyectoFinal.service;

import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoPostDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoPutDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaMapper;
import com.MisRaices.ProyectoFinal.entity.TarjetaCredito;
import com.MisRaices.ProyectoFinal.entity.Usuario;
import com.MisRaices.ProyectoFinal.interfaz.TarjetaCreditoInterfaz;
import com.MisRaices.ProyectoFinal.repository.TarjetaCreditoRepository;

import java.util.List;
import java.util.Optional;

import com.MisRaices.ProyectoFinal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaCreditoService implements TarjetaCreditoInterfaz {

    @Autowired
    private TarjetaCreditoRepository repo;
    @Autowired
    private TarjetaMapper mapper;
    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public TarjetaCreditoGetDTO crear(TarjetaCreditoPostDTO postDTO) {
        if (postDTO.getUsuario() == null || postDTO.getUsuario().getId() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        Optional<Usuario> userOpt = usuarioRepo.findById(postDTO.getUsuario().getId());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no válido");
        }

        TarjetaCredito tarjeta = mapper.toEntity(postDTO);
        tarjeta.setUsuario(userOpt.get());
        tarjeta.setSaldo(20000.0);

        TarjetaCredito tarjetaGuardada = repo.save(tarjeta);
        return mapper.toDTO(tarjetaGuardada);
    }


    @Override
    public void eliminar(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Tarjeta no encontrada");
        }
        repo.deleteById(id);
    }

    @Override
    public Optional<TarjetaCreditoGetDTO> obtener(Integer id) {
        return repo.findById(id).map(tarjetaCredito -> mapper.toDTO(tarjetaCredito));
    }

    @Override
    public List<TarjetaCreditoGetDTO> listar() {
        return mapper.dtoList(repo.findAll());
    }

    @Override
    public TarjetaCreditoGetDTO actualizar(Integer id, TarjetaCreditoPutDTO putDTO) {
        TarjetaCredito tarjetaExistente = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada"));
        tarjetaExistente = mapper.actualizar(tarjetaExistente, putDTO);
        TarjetaCredito tarjetaActualizada = repo.save(tarjetaExistente);
        return mapper.toDTO(tarjetaActualizada);
    }

}
