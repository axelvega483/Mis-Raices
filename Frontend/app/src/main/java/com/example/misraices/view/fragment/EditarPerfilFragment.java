package com.example.misraices.view.fragment;

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
        init();
        initListener();
        return binding.getRoot();

    }

    private void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuarioViewModel.obtenerUsuario().observe(getViewLifecycleOwner(), usuarioList -> {
            if (usuarioList != null && !usuarioList.isEmpty()) {
                usuarioActual = usuarioList.get(0);

                binding.nombreEditText.setText(usuarioActual.getNombre());
                binding.apellidoEditText.setText(usuarioActual.getApellido());
                binding.direccionEditText.setText(usuarioActual.getDireccion());
                binding.telefonoEditText.setText(String.valueOf(usuarioActual.getTelefono()));
            }
        });
    }
    private void initListener() {
        binding.btnActualizar.setOnClickListener(view -> {
            if (usuarioActual != null) {
                String nombre = binding.nombreEditText.getText().toString().trim();
                String apellido = binding.apellidoEditText.getText().toString().trim();
                String direccion = binding.direccionEditText.getText().toString().trim();
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
                usuarioActual.setDireccion(direccion);
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