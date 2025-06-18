package com.example.misraices.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.Direccion;
import com.example.misraices.databinding.FragmentMapaBinding;
import com.example.misraices.viewModel.UsuarioViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    FragmentMapaBinding binding;
    private GoogleMap mMap;
    private LatLng seleccion;
    private UsuarioViewModel usuarioViewModel;
    private int usuarioId;
    private FusedLocationProviderClient fusedLocationClient;


    public MapaFragment() {
        // Required empty public constructor
    }


    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
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
        binding = FragmentMapaBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();
    }

    public void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    public void initlistener() {
        binding.confirmarUbicacionBtn.setOnClickListener(v -> {
            if (seleccion != null) {
                try {
                    Direccion direccion = obtenerDireccionDesde(seleccion);
                    if (direccion != null) {
                        usuarioViewModel.actualizarDireccion(usuarioId, direccion)
                                .observe(getViewLifecycleOwner(), result -> {
                                    if (result != null && result.isExito()) {
                                        usuarioViewModel.setDireccionActualizada(true);
                                        HomeFragment homeFragment =  HomeFragment.newInstance();
                                        getParentFragmentManager().beginTransaction()
                                                .replace(R.id.frameContainer, homeFragment)
                                                .commit();
                                    } else {
                                        Toast.makeText(requireContext(), "Error al actualizar dirección", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(requireContext(), "No se pudo obtener la dirección", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "No se pudo obtener la dirección", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private Direccion obtenerDireccionDesde(LatLng seleccion) throws IOException {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        List<Address> direcciones = geocoder.getFromLocation(seleccion.latitude, seleccion.longitude, 1);
        if (!direcciones.isEmpty()) {
            Address address = direcciones.get(0);
            Direccion direccion = new Direccion();
            direccion.setCalle(address.getThoroughfare());
            direccion.setNumero(Long.parseLong(address.getSubThoroughfare()));
            direccion.setCiudad(address.getLocality());
            direccion.setProvincia(address.getAdminArea());
            direccion.setCodigoPostal(address.getPostalCode());
            direccion.setLatitud(seleccion.latitude);
            direccion.setLongitud(seleccion.longitude);
            return direccion;
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);


            usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
                if (usuario != null && usuario.getData().getDireccion() != null) {
                    Direccion direccion = usuario.getData().getDireccion();
                    LatLng ubicacionGuardada = new LatLng(direccion.getLatitud(), direccion.getLongitud());

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionGuardada, 15));
                    mMap.addMarker(new MarkerOptions().position(ubicacionGuardada).title("Tu dirección guardada"));
                    seleccion = ubicacionGuardada;
                } else {
                    moverCamaraAUbicacionActual();
                }
            });
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        }

        mMap.setOnMapClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));
            seleccion = latLng;
        });
    }


    @SuppressLint("MissingPermission")
    private void moverCamaraAUbicacionActual() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 15));
                mMap.addMarker(new MarkerOptions().position(ubicacionActual).title("Tu ubicación"));
                seleccion = ubicacionActual;
            } else {
                Toast.makeText(requireContext(), "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    moverCamaraAUbicacionActual();
                }
            } else {
                Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}