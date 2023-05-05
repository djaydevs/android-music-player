package com.example.playlistapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    TextView tvLogout;
    ListView listView;
    Button btnSearch, btnStop;
    ImageView imgProfile;

    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        listView = (ListView) findViewById(R.id.lvSongs);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnStop = (Button) findViewById(R.id.btnStopMusic);
        imgProfile = (ImageView) findViewById(R.id.imgAccountProfile);

        accountProfile();
        setDataModels();

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(PlaylistActivity.this, MusicService.class));
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDataModels() {
        dataModels = new ArrayList<>();

        dataModels.add(new DataModel("Cruel Summer", "Taylor Swift", "02:59"));
        dataModels.add(new DataModel("Don't Blame Me", "Taylor Swift", "03:56"));
        dataModels.add(new DataModel("As It Was", "Harry Styles", "02:45"));

        adapter = new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);
    }

    private void accountProfile() {
        // Get the file path of the saved image
        String filePath = "/storage/emulated/0/Android/data/com.example.playlistapp/files/my_image.jpg";

        // Create a File object from the file path
        File file = new File(filePath);

        // Check if the file exists and is a file (not a directory)
        if (file.exists() && file.isFile()) {
            // Load the image into an ImageView or any other view that supports displaying images
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imgProfile.setImageBitmap(bitmap);
        } else {
            // Handle the case when the file does not exist or is not a file
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }

        Bitmap originalBitmap = BitmapFactory.decodeFile(filePath);
        Bitmap bitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setShader(new BitmapShader(originalBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        canvas.drawCircle(originalBitmap.getWidth() / 2f, originalBitmap.getHeight() / 2f, originalBitmap.getWidth() / 2f, paint);

        // Set the circular bitmap to the ImageView
        imgProfile.setImageBitmap(bitmap);
    }
}