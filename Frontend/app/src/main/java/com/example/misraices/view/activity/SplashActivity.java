package com.example.misraices.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.misraices.R;
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
            boolean logueado = prefs.getBoolean("logueado", false);

            Intent intent;
            if (logueado) {
                intent = new Intent(SplashActivity.this, PrincipalActivity.class); // ya logueado
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class); // necesita loguearse
            }
            startActivity(intent);
            finish();
        }, 3000);
    }
}
