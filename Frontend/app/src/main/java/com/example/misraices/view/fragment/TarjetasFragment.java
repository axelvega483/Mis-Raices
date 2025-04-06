package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misraices.R;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentTarjetasBinding;
import com.example.misraices.view.adapter.AdapterTarjetaCompra;
import com.example.misraices.view.adapter.AdapterTarjetaDetalle;
import com.example.misraices.viewModel.TarjetaViewModel;


public class TarjetasFragment extends Fragment {
    private FragmentTarjetasBinding binding;
    private TarjetaViewModel tarjetaViewModel;

    public TarjetasFragment() {
        // Required empty public constructor
    }

    public static TarjetasFragment newInstance() {
        TarjetasFragment fragment = new TarjetasFragment();
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
        binding = FragmentTarjetasBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
    }

    private void initListener() {
        tarjetaViewModel.obtenerTarjetas().observe(getViewLifecycleOwner(), tarjetas -> {
            if (tarjetas != null) {
                binding.recyclerViewTarjetas.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewTarjetas.setAdapter(new AdapterTarjetaDetalle(tarjetas, getContext(), this::abrirtarjetaDetalle));
            }
        }); binding.btnAgregar.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, NewTarjetaFragment.newInstance()).addToBackStack(null).commit();
        });
    }

    private void abrirtarjetaDetalle(TarjetaCredito tarjetaCredito) {
        TarjetaDetalleFragment fragment = new TarjetaDetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tarjeta", tarjetaCredito);
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .addToBackStack(null)
                .commit();



    }

}