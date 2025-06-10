package com.example.misraices.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.misraices.R;
import com.facebook.stetho.BuildConfig;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);


        SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
        int ultimaVersionGuardada = prefs.getInt("ultimaVersion", -1);
        int versionActual = BuildConfig.VERSION_CODE;

        if (ultimaVersionGuardada < versionActual) {
            Log.d("VERSION_APP", "Versión actualizada: " + ultimaVersionGuardada + " ➜ " + versionActual);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("ultimaVersion", versionActual);
            editor.apply();
        }
        new Handler().postDelayed(() -> {
            boolean logueado = prefs.getBoolean("logueado", false);

            Intent intent;
            if (logueado) {
                intent = new Intent(SplashActivity.this, PrincipalActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 3000);
    }
}
