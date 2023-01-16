package com.afundacion.gestorfinanzas.Launcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzas.Screens.LoginActivity;


public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this , LoginActivity .class);
        startActivity(intent);
    }
}