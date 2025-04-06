package com.example.misraices.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.misraices.R;
import com.example.misraices.databinding.ActivityPrincipalBinding;
import com.example.misraices.view.fragment.CarritoFragment;
import com.example.misraices.view.fragment.HomeFragment;
import com.example.misraices.view.fragment.PedidoRealizadoFragment;
import com.example.misraices.view.fragment.PerfilFragment;
import com.google.android.material.navigation.NavigationBarView;

public class PrincipalActivity extends AppCompatActivity {
    private ActivityPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initlistener();
    }

    public void init() {
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        openFragment(HomeFragment.newInstance());
    }

    public void initlistener() {
        binding.btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    openFragment(HomeFragment.newInstance());
                } else if (itemId == R.id.nav_carrito) {
                    openFragment(CarritoFragment.newInstance());

                } else if (itemId == R.id.nav_pedido) {
                    openFragment(PedidoRealizadoFragment.newInstance());
                } else if (itemId==R.id.nav_perfil) {
                    openFragment(PerfilFragment.newInstance());
                }
                return true;
            }
        });
    }

    public void openFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, fragment)
                    .commit();
        } else {
            // Otros fragments pueden ser agregados al BackStack
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
