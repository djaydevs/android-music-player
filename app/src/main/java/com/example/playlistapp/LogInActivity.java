package com.example.playlistapp;

import android.content.Intent;
import android.database.Cursor;
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

                // Query the database for a matching record
                Cursor cursor = dbHelper.UserLogin(email, password);

                if (cursor.getCount() == 0) {
                    // Authentication successful
                    Toast.makeText(LogInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this, PlaylistActivity.class);
                    startActivity(intent);
                } else {
                    // Authentication failed
                    Toast.makeText(LogInActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                //onDestroy();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}