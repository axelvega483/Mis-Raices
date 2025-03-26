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
import com.example.misraices.databinding.FragmentLoginBinding;
import com.example.misraices.viewModel.UsuarioViewModel;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private UsuarioViewModel usuarioViewModel;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();

    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class); // Inicializa el ViewModel aquí

        usuarioViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Log.e("usuario ", user.getCorreo());
            } else {
                Log.e("usuario", "usuario es null");
            }
        });
    }

    public void initlistener() {
        binding.textPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecuperarPasswordFragment fragment = new RecuperarPasswordFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
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
        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario user = usuarioViewModel.getUsuarioLiveData().getValue();
                if (user != null) {
                    usuarioViewModel.login(user);
                    Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}