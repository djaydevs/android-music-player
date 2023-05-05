package com.example.playlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    EditText etEmail, etPassword;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.equals("") && password.equals("")) {
                    Toast.makeText(LogInActivity.this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result = dbHelper.UserLogin(email, password);
                    if (result == false) {
                        Toast.makeText(LogInActivity.this, "Invalid email and password!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LogInActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, PlaylistActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}