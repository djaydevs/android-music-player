package com.example.playlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().equals("tswift@gmail.com")
                        && etPass.getText().toString().equals("tswift13")) {
                    Toast.makeText(LogInActivity.this, "Login Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this, PlaylistActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LogInActivity.this, "Incorrect email or password!",Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etPass.setText("");
                }
            }
        });
    }
}