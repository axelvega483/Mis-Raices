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
import com.example.misraices.databinding.FragmentCodigoCuentaBinding;
import com.example.misraices.viewModel.UsuarioViewModel;

public class CodigoCuentaFragment extends Fragment {
    private FragmentCodigoCuentaBinding binding;
    private UsuarioViewModel usuarioViewModel;

    public CodigoCuentaFragment() {
        // Required empty public constructor
    }

    public static CodigoCuentaFragment newInstance() {
        CodigoCuentaFragment fragment = new CodigoCuentaFragment();
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
        binding = FragmentCodigoCuentaBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();
    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        if(usuarioViewModel.getUsuarioLiveData().getValue() == null){
            Log.e("es nulo", "livedata nulo");
        }
    }

    public void initlistener() {
        binding.btnCodigoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("entrabtn", "boton codigo");
                Usuario user = usuarioViewModel.getUsuarioLiveData().getValue();
                if (user == null) {
                    Log.e("Error", "Usuario es null");
                    Toast.makeText(getContext(), "Error al activar la cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.e("usuario correo", user.getCorreo());
                user.setCodigo(binding.codigoTxt.getText().toString().toUpperCase());
                Log.e("codigo asignado", user.getCodigo());

                usuarioViewModel.activarCuenta(user);
            }
        });
    }


}