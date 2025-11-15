package com.MisRaices.ProyectoFinal.interfaz;

import java.util.Optional;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioPutDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioRegistroDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioRestablecerPasswordDTO;
import com.MisRaices.ProyectoFinal.entity.Direccion;

public interface UsuarioInterfaz {

    UsuarioGetDTO crear(UsuarioRegistroDTO registroDTO);

    Optional<UsuarioGetDTO> obtener(Integer id);

    UsuarioGetDTO actualizar(Integer id, UsuarioPutDTO putDTO);

    Optional<UsuarioGetDTO> findByCorreoAndPassword(String correo, String password);

    Optional<UsuarioGetDTO> findByCorreoAndCodigo(String correo, String codigo);

    Optional<UsuarioGetDTO> findByCorreo(String correo);

    Optional<UsuarioGetDTO> findByToken(UsuarioRestablecerPasswordDTO restablecerDTO);

    UsuarioGetDTO cargarDireccion(Integer usuario, Direccion direccion);
}
