package com.example.examen2doparcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PongGame pongGame = new PongGame(getApplicationContext());
        pongGame.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(pongGame);
    }
}