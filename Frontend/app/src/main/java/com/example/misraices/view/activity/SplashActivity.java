package com.example.misraices.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.misraices.R;
import com.example.misraices.viewModel.UsuarioViewModel;
import com.facebook.stetho.BuildConfig;

import androidx.emoji.text.EmojiCompat;
import androidx.emoji.bundled.BundledEmojiCompatConfig;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        UsuarioViewModel usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
        int ultimaVersionGuardada = prefs.getInt("ultimaVersion", -1);
        int versionActual = BuildConfig.VERSION_CODE;

        if (ultimaVersionGuardada < versionActual) {
            Log.d("VERSION_APP", "Versión actualizada: " + ultimaVersionGuardada + " ➜ " + versionActual);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("ultimaVersion", versionActual);
            editor.apply();
        }
        usuarioViewModel.verificarBackend(isBackendActivo -> {
            Log.e("entra", "entra");
            if (isBackendActivo) {
                Log.e("Splash", "Backend OK");
                boolean logueado = prefs.getBoolean("logueado", false);
                Intent intent = new Intent(
                        SplashActivity.this,
                        logueado ? PrincipalActivity.class : MainActivity.class
                );
                startActivity(intent);
                finish();
            } else {
                Log.e("Splash", "Backend caído");
                Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_BACKEND_ERROR, true);
                startActivity(intent);
                finish();
            }
        });
    }
}
