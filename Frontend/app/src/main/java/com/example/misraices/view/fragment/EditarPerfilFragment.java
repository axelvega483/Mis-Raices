package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.data.model.Direccion;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.databinding.FragmentEditarPerfilBinding;
import com.example.misraices.viewModel.UsuarioViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class EditarPerfilFragment extends Fragment {
    private FragmentEditarPerfilBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private int usuarioId;
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
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);

        init();
        initListener();
        return binding.getRoot();

    }

    private void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.nombreEditText.setText(usuario.getData().getNombre());
                binding.apellidoEditText.setText(usuario.getData().getApellido());
                binding.telefonoEditText.setText(String.valueOf(usuario.getData().getTelefono()));
                if (usuario.getData().getDireccion() == null) {
                    binding.calleEditText.setText("");
                } else {
                    binding.calleEditText.setText(usuario.getData().getDireccion().getCalle());
                    binding.numeroEditText.setText(String.valueOf(usuario.getData().getDireccion().getNumero()));
                }
                usuarioActual = usuario.getData();
            } else {
                Toast.makeText(getContext(), "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        binding.btnActualizar.setOnClickListener(view -> {
            if (usuarioActual != null) {
                String nombre = binding.nombreEditText.getText().toString().trim();
                String apellido = binding.apellidoEditText.getText().toString().trim();
                String telefonoStr = binding.telefonoEditText.getText().toString().trim();
                String calle = binding.calleEditText.getText().toString().trim();
                String numeroStr = binding.numeroEditText.getText().toString().trim();
                String actualPassword = binding.passwordBDEditText.getText().toString();
                String newPassword = binding.passwordEditText.getText().toString();
                String confirmPassword = binding.confirPassEditText.getText().toString();

                Long telefono;
                try {
                    telefono = Long.parseLong(telefonoStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Ingrese un número de teléfono válido.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long numero;
                try {
                    numero = Long.parseLong(numeroStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Ingrese una numeración de calle válida.", Toast.LENGTH_SHORT).show();
                    return;
                }

                usuarioActual.setNombre(nombre);
                usuarioActual.setApellido(apellido);
                usuarioActual.setTelefono(telefono);

                if (usuarioActual.getDireccion() == null) {
                    Toast.makeText(getContext(), "No se puede actualizar la dirección.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Configure una dirección desde el home.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Direccion direccion = usuarioActual.getDireccion();
                    direccion.setCalle(calle);
                    direccion.setNumero(numero);

                    LatLng coordenadas = obtenerLatLngDesdeTexto(direccion);
                    if (coordenadas != null) {
                        direccion.setLatitud(coordenadas.latitude);
                        direccion.setLongitud(coordenadas.longitude);
                    } else {
                        Toast.makeText(getContext(), "No se pudo obtener coordenadas para la nueva dirección.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String passBD = usuarioActual.getPassword();

                // Validación de contraseña
                if (!newPassword.isEmpty()) {
                    if (!passBD.equals(actualPassword)) {
                        Toast.makeText(getContext(), "La contraseña actual ingresada es incorrecta.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newPassword.equals(confirmPassword)) {
                        Toast.makeText(getContext(), "Las nuevas contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    usuarioActual.setPassword(newPassword);
                }

                Log.e("EditarPerfil", "ID: " + usuarioActual.getId());
                Log.d("EditarPerfil", "Nombre: " + usuarioActual.getNombre());

                usuarioViewModel.editarUsuario(usuarioActual.getId(), usuarioActual)
                        .observe(getViewLifecycleOwner(), result -> {
                            if (result.getData() != null) {
                                Toast.makeText(getContext(), "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getContext(), "No se pudo actualizar el perfil. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                                Log.e("EditarPerfil", "Error: " + result.getMensaje());
                            }
                        });
            }
        });
    }

    private LatLng obtenerLatLngDesdeTexto(Direccion direccion) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        String direccionCompleta = direccion.getCalle() + " " + direccion.getNumero() + ", " +
                direccion.getCiudad() + ", " + direccion.getProvincia();

        try {
            List<Address> resultados = geocoder.getFromLocationName(direccionCompleta, 1);
            if (resultados != null && !resultados.isEmpty()) {
                Address resultado = resultados.get(0);
                return new LatLng(resultado.getLatitude(), resultado.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}