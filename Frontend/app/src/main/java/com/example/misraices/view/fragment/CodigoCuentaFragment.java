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
import com.example.misraices.databinding.FragmentCodigoCuentaBinding;
import com.example.misraices.viewModel.UsuarioViewModel;

public class CodigoCuentaFragment extends Fragment {
    private FragmentCodigoCuentaBinding binding;
    private UsuarioViewModel usuarioViewModel;

    public CodigoCuentaFragment() {
        // Constructor vacío requerido
    }

    public static CodigoCuentaFragment newInstance() {
        return new CodigoCuentaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCodigoCuentaBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        Log.e("usuarioViewModel", usuarioViewModel.toString());
    }

    public void initListener() {
        binding.btnCodigoCuenta.setOnClickListener(view -> {

            Log.e("entraBtn", "entra");
            String codigo = binding.codigoTxt.getText().toString().toUpperCase();
            if (codigo.isEmpty()) {
                Toast.makeText(getContext(), "Por favor ingrese el código de cuenta", Toast.LENGTH_SHORT).show();
                return;
            }
            usuarioViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), usuario -> {
                if (usuario == null) {
                    Toast.makeText(getContext(), "Error al obtener el usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                usuario.setCodigo(codigo);
                Log.e("usuario con código", usuario.toString());


                usuarioViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), user -> {
                    if (user == null) {
                        Toast.makeText(getContext(), "Error al obtener el usuario", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    usuarioViewModel.activarCuenta(user);


                    Toast.makeText(getContext(), "Cuenta activada con éxito", Toast.LENGTH_SHORT).show();
                    Log.e("usuario activado", user.toString());

                    usuarioViewModel.setUsuarioLiveData(user);
                    Log.e("usuario activado actualizado", user.toString());
                    // Navegar a LoginFragment
                    LoginFragment login = new LoginFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, login)
                            .commit();

                });
            });
        });
    }

}