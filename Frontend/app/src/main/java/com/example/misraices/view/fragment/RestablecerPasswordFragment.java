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
import com.example.misraices.databinding.FragmentRestablecerPasswordBinding;
import com.example.misraices.viewModel.UsuarioViewModel;


public class RestablecerPasswordFragment extends Fragment {
    private FragmentRestablecerPasswordBinding binding;
    private UsuarioViewModel usuarioViewModel;

    public RestablecerPasswordFragment() {
        // Required empty public constructor
    }


    public static RestablecerPasswordFragment newInstance() {
        RestablecerPasswordFragment fragment = new RestablecerPasswordFragment();
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
        binding = FragmentRestablecerPasswordBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();

    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);

    }

    public void initlistener() {
        binding.btnPassword.setOnClickListener(view -> {
            String token = binding.tokenTxt.getText().toString().toUpperCase();
            String pass = binding.passwordEditText.getText().toString();
            String newpass = binding.newpasswordEditText.getText().toString();
            if (token.isEmpty()) {
                Toast.makeText(getContext(), "Por favor ingrese el código de verificación", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.isEmpty() || newpass.isEmpty()) {
                Toast.makeText(getContext(), "Por favor ingrese las contraseñas", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pass.equals(newpass)) {
                Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }
            Usuario usuario = new Usuario();
            usuario.setToken(token);
            usuario.setPassword(pass);
            usuarioViewModel.restablecerPassword(usuario).observe(getViewLifecycleOwner(), result -> {
                Log.e("usuario actualizado",result.getData().toString());
                Toast.makeText(getContext(), "Contraseña restablecida con éxito", Toast.LENGTH_SHORT).show();
                usuarioViewModel.setUsuarioLiveData(result.getData());

            });


            LoginFragment fragment = new LoginFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        });
    }
}