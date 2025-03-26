package com.example.misraices.data.repository;

import android.content.Context;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.service.UsuarioService;

public class UsuarioRepository {
    private final UsuarioService usuarioService;

    public UsuarioRepository(Context context) {
        usuarioService = ApiRetrofit.getRetrofitInstance(context).create(UsuarioService.class);
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }
}
