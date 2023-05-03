package com.example.playlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText etName, etEmail, etPassword, etContact;
    Button btnSave, btnCancel;
    ImageButton btnCamera, btnGallery;
    ImageView btnMail, btnContact;
    AlertDialog alertDialog;

    int camera, gallery, sms;

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

        camera = 1;
        gallery = 2;
        sms = 3;

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, camera);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomEmailDialog();
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomContactDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = etName.getText().toString();
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();
                String Contact = etContact.getText().toString();

                if(!isEmailValid(Email)){
                    Toast.makeText(SignUpActivity.this, "Not a valid email, please try again.", Toast.LENGTH_SHORT).show();
                }
                else if(!isPasswordValid(Password)){
                    Toast.makeText(SignUpActivity.this, "Please insert more than 6 characters.",Toast.LENGTH_SHORT) .show();
                }
                else if(Email.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Email field required!", Toast.LENGTH_SHORT).show();
                }
                else if(Password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password field required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result = dbHelper.InsertRecord(Email, Password, Name, Contact);
                    if (result == true) {
                        Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        refresh();
                        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Sign-up failed!", Toast.LENGTH_SHORT).show();
                    }
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

    private void showCustomEmailDialog() {
        // Inflate the custom layout file
        View dialogView = LayoutInflater.from(this).inflate(R.layout.email_layout, null);

        // Create a new AlertDialog object and set the custom layout as its view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        ImageView closeButton = dialogView.findViewById(R.id.btnClose);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog when the button is clicked
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showCustomContactDialog() {
        // Inflate the custom layout file
        View dialogView = LayoutInflater.from(this).inflate(R.layout.message_layout, null);

        // Create a new AlertDialog object and set the custom layout as its view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        ImageView closeButton = dialogView.findViewById(R.id.btnClose2);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog when the button is clicked
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
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