package com.example.playlistapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    //Main
    DatabaseHelper dbHelper;
    EditText etName, etEmail, etPassword, etContact;
    Button btnSave, btnCancel;
    ImageButton btnCamera, btnGallery;
    ImageView btnMail, btnContact, imgProfile;
    AlertDialog alertDialog;

    //Custom Email
    TextView tvEmail;
    EditText etEmailTo, etSubject, etEmailMessage;
    Button btnSendEmail;
    ImageView closeEmailButton;

    //Custom Message
    TextView tvContact;
    EditText etContactMessage;
    ImageView closeContactButton;
    Button btnSendContact;

    int CAMERA, GALLERY, SMS;

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

        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        CAMERA = 1;
        GALLERY = 2;
        SMS = 3;

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
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
               saveDataToDatabase();
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

    private void cameraIntent() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && reqCode == CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgProfile.setImageBitmap(bitmap);
        }
        if (resultCode == Activity.RESULT_OK && reqCode == GALLERY) {
            Uri image = data.getData();
            imgProfile.setImageURI(image);
        }
        // Get the dimensions of the ImageView
        int width = imgProfile.getWidth();
        int height = imgProfile.getHeight();

        // Create a Bitmap object from the ImageView
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imgProfile.draw(canvas);

        // Create a new file in the app's private directory
        File file = new File(getExternalFilesDir(null), "my_image.jpg");

        try {
            // Create a FileOutputStream object for the file
            FileOutputStream fos = new FileOutputStream(file);

            // Compress the Bitmap and write it to the FileOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // Flush and close the FileOutputStream
            fos.flush();
            fos.close();

            // Display a success message
            //Toast.makeText(this, "Image saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Image saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCustomEmailDialog() {
        // Inflate the custom layout file
        View dialogView = LayoutInflater.from(this).inflate(R.layout.email_layout, null);

        // Create a new AlertDialog object and set the custom layout as its view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        tvEmail = dialogView.findViewById(R.id.tvEmailFrom);
        etEmailTo = dialogView.findViewById(R.id.etEmailTo);
        etSubject = dialogView.findViewById(R.id.etSubject);
        etEmailMessage = dialogView.findViewById(R.id.etEmailMessage);
        btnSendEmail = dialogView.findViewById(R.id.btnSendEmail);
        closeEmailButton = dialogView.findViewById(R.id.btnClose);

        tvEmail.setText(etEmail.getText().toString());

        closeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog when the button is clicked
                alertDialog.dismiss();
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{etEmailTo.getText().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, etEmailMessage.getText().toString());

                Intent chooser = Intent.createChooser(intent, "Send message via...");
                startActivity(chooser);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showCustomContactDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.message_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        tvContact = dialogView.findViewById(R.id.tvNumberTo);
        etContactMessage = dialogView.findViewById(R.id.etContactMessage);
        closeContactButton = dialogView.findViewById(R.id.btnClose2);
        btnSendContact = dialogView.findViewById(R.id.btnSendContact);

        tvContact.setText(etContact.getText().toString());

        closeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnSendContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ContextCompat.checkSelfPermission(SignUpActivity.this, android.Manifest.permission.SEND_SMS))!= PackageManager.PERMISSION_GRANTED){
                    //ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, SMS);
                    if(ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this,android.Manifest.permission.SEND_SMS)) {

                    }
                }
                else{
                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, SMS);
                }
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        String Contact = tvContact.getText().toString();
        String Message = etContactMessage.getText().toString();

        if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(Contact, null, Message, null, null);
            Toast.makeText(this, "Message sent!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Message not sent !", Toast.LENGTH_LONG).show();
        }
    }

    private void saveDataToDatabase() {
        String Name = etName.getText().toString();
        String Email = etEmail.getText().toString();
        String Password = etPassword.getText().toString();
        String Contact = etContact.getText().toString();

        if(!isEmailValid(Email)){
            etEmail.setError("Not a valid email!");
        }
        else if(!isPasswordValid(Password)){
            etPassword.setError("Set 7 characters or more!");
        }
        else if(!Contact.startsWith("09")) {
            etContact.setError("Start with \"09\"!");
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
                Toast.makeText(SignUpActivity.this, "Sign-up failed! Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
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