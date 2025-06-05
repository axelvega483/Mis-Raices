package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.misraices.R;
import com.example.misraices.data.model.Direccion;
import com.example.misraices.databinding.FragmentPerfilBinding;
import com.example.misraices.view.activity.MainActivity;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private Uri imgUri;
    private int usuarioId;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarImg();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        if (usuarioId != -1) {
            File file = new File(requireContext().getFilesDir(), "foto_perfil_" + usuarioId + ".jpg");
            if (file.exists()) {
                Glide.with(this).load(file).into(binding.ImgPerfil);
            }
        }

    }

    private void initListener() {
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            Log.e("usuarioPerfil", usuario.toString());
            if (usuario != null && usuario.getData() != null) {
                Log.e("usuarioPerfil", usuario.getData().toString());
                Direccion direccion = usuario.getData().getDireccion();
                binding.NombrePerfilTxt.setText(usuario.getData().getNombre() + " " + usuario.getData().getApellido());
                binding.CorreoPerfilTxt.setText("Correo: " + usuario.getData().getCorreo().toLowerCase());
                binding.TelefonoPerfilTxt.setText("Telefono: " + usuario.getData().getTelefono().toString());
                if (usuario.getData().getDireccion() != null) {
                    binding.DireccionPerfilTxt.setText("Dirección: " + direccion.getCalle() + " " + direccion.getNumero());
                } else {
                    binding.DireccionPerfilTxt.setText("Dirección: ");
                }
            } else {
                Log.e("usuarioPerfil", "Usuario o data es null");
                Toast.makeText(getContext(), "No se pudo cargar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnMisPlantas.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, MisPlantasFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        binding.btnCerrarSesion.setOnClickListener(view -> cerrarSesion());

        binding.ImgPerfil.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
        binding.btnEditarPerfil.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, EditarPerfilFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });
        binding.btnTarjetas.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, TarjetasFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });
    }


    private void cerrarSesion() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    public void cargarImg() {
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Glide.with(this).load(uri).into(binding.ImgPerfil);
                imgUri = uri;

                try {
                    InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                    File file = new File(requireContext().getFilesDir(), "foto_perfil_" + usuarioId + ".jpg");
                    FileOutputStream outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.close();
                    inputStream.close();
                    Log.d("Guardar imagen", "Imagen guardada en: " + file.getAbsolutePath());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Guardar imagen", "Error al guardar la imagen");
                }
            } else {
                Log.d("Media Picker", "No se seleccionó ninguna imagen");
            }
        });
    }

}