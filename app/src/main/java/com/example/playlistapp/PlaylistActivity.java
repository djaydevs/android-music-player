package com.example.playlistapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    TextView tvLogout, tvViewAllSongs;
    ListView listView;
    Button btnSearch, btnStop;
    ImageView imgProfile;
    AlertDialog alertDialog;

    //  Custom Search
    EditText etSearch;
    Button btnSearchMusic;
    ImageView closeSearchButton;

    private static CustomAdapter adapterForDataModels;
    private static CustomAdapter adapterForSearchMusic;
    CustomAdapter adapterForStopMusic;

//    private MusicService.MyBinder binder;
//    private boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        listView = (ListView) findViewById(R.id.lvSongs);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        tvViewAllSongs = (TextView) findViewById(R.id.tvViewAllSongs);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnStop = (Button) findViewById(R.id.btnStopMusic);
        imgProfile = (ImageView) findViewById(R.id.imgAccountProfile);

        accountProfile();

        setDataModels();
        adapterForDataModels = new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapterForDataModels);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomSearchMusic();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopService(new Intent(PlaylistActivity.this, MusicService.class));
                stopPlayingMusic();
            }
        });

        tvViewAllSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvViewAllSongs.setPaintFlags(tvViewAllSongs.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                tvViewAllSongs.setTextColor(getResources().getColor(R.color.white));
                //String filterText = etSearch.getText().toString();
                //adapterForSearchMusic = new CustomAdapter(dataModels,getApplicationContext());
                //adapterForDataModels.filter(filterText);

                // Clear the ListView before adding the filtered data
                listView.setAdapter(null);
                listView.setAdapter(adapterForDataModels);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayingMusic();
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
        dataModels.add(new DataModel("Falling", "Harry Styles", "04:00"));
        dataModels.add(new DataModel("timekeeper", "Greyson Chance", "03:57"));
        dataModels.add(new DataModel("Athena", "Greyson Chance", "03:58"));
        dataModels.add(new DataModel("Mastermind", "Taylor Swift", "03:11"));
        dataModels.add(new DataModel("august", "Taylor Swift", "04:21"));
    }

    private void accountProfile() {
        String filePath = "/storage/emulated/0/Android/data/com.example.playlistapp/files/my_image.jpg";
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imgProfile.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }

        //  The following code will make the ImageView crop to circle
        Bitmap originalBitmap = BitmapFactory.decodeFile(filePath);
        Bitmap bitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setShader(new BitmapShader(originalBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        canvas.drawCircle(originalBitmap.getWidth() / 2f, originalBitmap.getHeight() / 2f, originalBitmap.getWidth() / 2f, paint);

        imgProfile.setImageBitmap(bitmap);
    }

    private void showCustomSearchMusic() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.search_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        etSearch = dialogView.findViewById(R.id.etSearch);
        btnSearchMusic = dialogView.findViewById(R.id.btnSearchMusic);
        closeSearchButton = dialogView.findViewById(R.id.btnCLose3);

        setDataModels();

        btnSearchMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filterText = etSearch.getText().toString();
                adapterForSearchMusic = new CustomAdapter(dataModels,getApplicationContext());
                adapterForSearchMusic.filter(filterText);

                // Clear the ListView before adding the filtered data
                listView.setAdapter(null);
                listView.setAdapter(adapterForSearchMusic);
                alertDialog.dismiss();
            }
        });

        closeSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void stopPlayingMusic() {
        adapterForStopMusic = (CustomAdapter) listView.getAdapter();
        Toast.makeText(PlaylistActivity.this, "Music stopped!", Toast.LENGTH_SHORT).show();
        adapterForStopMusic.stopMusic();
    }
}