package com.example.user.reversi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityMatchSettings.class);
                startActivity(intent);
            }
        });
        Button playOnLineBtn = findViewById(R.id.online_btn);
        playOnLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.login_dialog, null);

                builder.setView(dialogView);

                EditText userNameET = dialogView.findViewById(R.id.input_username);
                EditText passwordET = dialogView.findViewById(R.id.input_password);
                Button loginBtn = dialogView.findViewById(R.id.login_btn);
                Button singUpBtn = dialogView.findViewById(R.id.sing_up_btn);

                final AlertDialog dialog = builder.create();

                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                singUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.show();
            }
        });
        Button settingBtn = findViewById(R.id.setting_btn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivitySettings.class);
                startActivity(intent);
            }
        });
        Button recordsBtn = findViewById(R.id.records_btn);
        recordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button exitBtn = findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
