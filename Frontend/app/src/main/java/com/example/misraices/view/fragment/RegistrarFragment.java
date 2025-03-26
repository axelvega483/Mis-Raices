package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.databinding.FragmentRegistrarBinding;
import com.example.misraices.viewModel.UsuarioViewModel;


public class RegistrarFragment extends Fragment {
    private FragmentRegistrarBinding binding;
    private UsuarioViewModel usuarioViewModel;

    public RegistrarFragment() {
        // Required empty public constructor
    }


    public static RegistrarFragment newInstance() {
        RegistrarFragment fragment = new RegistrarFragment();
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
        binding = FragmentRegistrarBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();
    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

    }

    public void initlistener() {
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();
                usuario.setNombre(binding.nombreEditText.getText().toString());
                usuario.setApellido(binding.apellidoEditText.getText().toString());
                usuario.setTelefono(Long.valueOf(binding.telefonoEditText.getText().toString()));
                usuario.setCorreo(binding.CorreoEditText.getText().toString());
                String password = binding.passwordEditText.getText().toString();
                String confirmPassword = binding.confirPassEditText.getText().toString();
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                usuario.setPassword(binding.passwordEditText.getText().toString());

                usuarioViewModel.crearUsuario(usuario);


                Toast.makeText(getContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

                CodigoCuentaFragment codigoCuenta = new CodigoCuentaFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, codigoCuenta)
                        .addToBackStack(null)
                        .commit();


                usuarioViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
                    Log.e("Error Registrar ", error);
                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                });

            }
        });
        binding.textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment login = new LoginFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, login)
                        .commit();
            }
        });

    }
}