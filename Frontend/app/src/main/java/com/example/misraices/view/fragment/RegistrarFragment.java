package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Patterns;
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
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);

    }

    public void initlistener() {
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usuario usuario = new Usuario();
                usuario.setNombre(binding.nombreEditText.getText().toString());
                usuario.setApellido(binding.apellidoEditText.getText().toString());
                String telefonoStr = binding.telefonoEditText.getText().toString();
                usuario.setCorreo(binding.CorreoEditText.getText().toString());
                String password = binding.passwordEditText.getText().toString();
                String confirPass = binding.confirPassEditText.getText().toString();
                if (usuario.getNombre().isEmpty()) {
                    Toast.makeText(getContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (usuario.getApellido().isEmpty()) {
                    Toast.makeText(getContext(), "El apellido es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (telefonoStr.isEmpty()) {
                    Toast.makeText(getContext(), "El teléfono es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getContext(), "La contraseña es obligatoria", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirPass.isEmpty()) {
                    Toast.makeText(getContext(), "La confirmación de contraseña es obligatoria", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (usuario.getCorreo().isEmpty()) {
                    Toast.makeText(getContext(), "El correo es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(usuario.getCorreo()).matches()) {
                    Toast.makeText(getContext(), "El correo no es válido", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    usuario.setTelefono(Long.valueOf(telefonoStr));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Teléfono inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirPass)) {
                    Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                usuario.setPassword(password);

                usuarioViewModel.crearUsuario(usuario);
                usuarioViewModel.setUsuarioLiveData(usuario);
                Log.e("usuario", usuario.toString());

                Toast.makeText(getContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

                CodigoCuentaFragment codigoCuenta = new CodigoCuentaFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, codigoCuenta)
                        .addToBackStack(null)
                        .commit();

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