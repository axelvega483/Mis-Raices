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
import com.example.misraices.databinding.FragmentRecuperarPasswordBinding;
import com.example.misraices.viewModel.UsuarioViewModel;


public class RecuperarPasswordFragment extends Fragment {
    FragmentRecuperarPasswordBinding binding;
    private UsuarioViewModel usuarioViewModel;

    public RecuperarPasswordFragment() {
        // Required empty public constructor
    }


    public static RecuperarPasswordFragment newInstance() {
        RecuperarPasswordFragment fragment = new RecuperarPasswordFragment();
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
        binding = FragmentRecuperarPasswordBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();
    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
    }

    public void initlistener() {
        binding.btnEmail.setOnClickListener(view -> {
            String email = binding.emailEditText.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "El correo no es válido", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e("entra1 ", "entra1");
            Usuario usuario = new Usuario();
            usuario.setCorreo(email);
            usuarioViewModel.solicitarToken(usuario).observe(getViewLifecycleOwner(),result->{
                Log.e("entra","entra");
                Log.e("usuario actualizado", usuario.toString());
                Toast.makeText(getContext(), "Correo enviado con éxito", Toast.LENGTH_SHORT).show();

                usuarioViewModel.setUsuarioLiveData(result.getData());
            });


            RestablecerPasswordFragment fragment = new RestablecerPasswordFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit();

        });
    }
}