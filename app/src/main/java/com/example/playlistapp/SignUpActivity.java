package com.example.playlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText etName, etEmail, etPassword, etContact;
    Button btnSave, btnCancel;
    ImageButton btnCamera, btnGallery;
    ImageView btnMail, btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(SignUpActivity.this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmailSignUp);
        etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        etContact = (EditText) findViewById(R.id.etContact);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnGallery = (ImageButton) findViewById(R.id.btnGallery);
        btnMail = (ImageView) findViewById(R.id.btnMail);
        btnContact = (ImageView) findViewById(R.id.btnContact);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String contact = etContact.getText().toString();

                if(!isEmailValid(email)){
                    Toast.makeText(SignUpActivity.this, "Not a valid email, please try again.", Toast.LENGTH_SHORT).show();
                }
                else if(!isPasswordValid(password)){
                    Toast.makeText(SignUpActivity.this, "Please insert more than 6 characters.",Toast.LENGTH_SHORT) .show();
                }
                else if(email.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Email field required!", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password field required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isInserted= dbHelper.InsertRecord(email, password, name, contact);
                    Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    refresh();
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void refresh() {
        etName.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etContact.setText("");
    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String password){
        return password.length()>6;
    }
}