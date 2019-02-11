package com.example.user.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityMatchSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_settings);

        Button startBtn = findViewById(R.id.play_btn);
        ImageView blackPlayer = findViewById(R.id.black_stone);
        ImageView whitePlayer = findViewById(R.id.white_stone);
        ImageView randomPlayer = findViewById(R.id.random_stone);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMatchSettings.this, ActivityMatch.class);
                startActivity(intent);
            }
        });
        blackPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatchSettings.this, "black", Toast.LENGTH_SHORT).show();
            }
        });
        whitePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatchSettings.this, "white", Toast.LENGTH_SHORT).show();
            }
        });
        randomPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatchSettings.this, "random", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
