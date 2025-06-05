package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.databinding.FragmentEditarPerfilBinding;
import com.example.misraices.viewModel.UsuarioViewModel;

import javax.xml.transform.Result;


public class EditarPerfilFragment extends Fragment {
    private FragmentEditarPerfilBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private int usuarioId;
    private Usuario usuarioActual;

    public EditarPerfilFragment() {
        // Required empty public constructor
    }

    public static EditarPerfilFragment newInstance() {
        EditarPerfilFragment fragment = new EditarPerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditarPerfilBinding.inflate(inflater, container, false);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);

        init();
        initListener();
        return binding.getRoot();

    }

    private void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.nombreEditText.setText(usuario.getData().getNombre());
                binding.apellidoEditText.setText(usuario.getData().getApellido());
                binding.telefonoEditText.setText(String.valueOf(usuario.getData().getTelefono()));
                usuarioActual = usuario.getData();
            } else {
                Toast.makeText(getContext(), "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        binding.btnActualizar.setOnClickListener(view -> {
            if (usuarioActual != null) {
                String nombre = binding.nombreEditText.getText().toString().trim();
                String apellido = binding.apellidoEditText.getText().toString().trim();
                String telefonoStr = binding.telefonoEditText.getText().toString().trim();
                String actualPassword = binding.passwordBDEditText.getText().toString();
                String newPassword = binding.passwordEditText.getText().toString();
                String confirmPassword = binding.confirPassEditText.getText().toString();

                Long telefono;
                try {
                    telefono = Long.parseLong(telefonoStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Teléfono inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                usuarioActual.setNombre(nombre);
                usuarioActual.setApellido(apellido);
                usuarioActual.setTelefono(telefono);

                String passBD = usuarioActual.getPassword();

                // Validamos contraseña actual antes de permitir cambio
                if (!newPassword.isEmpty()) {
                    if (!passBD.equals(actualPassword)) {
                        Toast.makeText(getContext(), "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newPassword.equals(confirmPassword)) {
                        Toast.makeText(getContext(), "Las nuevas contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    usuarioActual.setPassword(newPassword);
                }

                Log.e("EditarPerfil", "ID: " + usuarioActual.getId());
                Log.d("EditarPerfil", "Nombre: " + usuarioActual.getNombre());

                usuarioViewModel.editarUsuario(usuarioActual.getId(), usuarioActual)
                        .observe(getViewLifecycleOwner(), result -> {
                            if (result.getData() != null) {
                                Toast.makeText(getContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getContext(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
                                Log.e("EditarPerfil", "Error: " + result.getError());
                            }
                        });
            }
        });
    }

}