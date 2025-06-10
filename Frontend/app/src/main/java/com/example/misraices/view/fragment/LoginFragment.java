package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.misraices.databinding.FragmentLoginBinding;
import com.example.misraices.view.activity.PrincipalActivity;
import com.example.misraices.viewModel.UsuarioViewModel;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();

    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);


    }

    public void initlistener() {
        binding.textCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecuperarPasswordFragment fragment = new RecuperarPasswordFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.textRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarFragment fragment = new RegistrarFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.btnIniciarSesion.setOnClickListener(view -> {

            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Por favor ingrese ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setCorreo(email);
            usuario.setPassword(password);

            usuarioViewModel.login(usuario).observe(getViewLifecycleOwner(), result -> {
                if (result.getData() != null) {
                    Log.e("result login", result.getData().toString());
                    Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    usuarioViewModel.setUsuarioLiveData(result.getData());

                    SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logueado", true);
                    editor.putInt("usuarioId", result.getData().getId());
                    editor.apply();
                    Log.e("usuario login", result.getData().toString());
                    Intent intent = new Intent(getContext(), PrincipalActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            });


        });
        usuarioViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                Toast.makeText(getContext(), "Error al obtener el usuario", Toast.LENGTH_SHORT).show();
                Log.e("usuario login nulo", user.toString());
                return;
            }

            Log.e("usuario login", user.toString());
        });
    }


}