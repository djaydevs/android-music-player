package com.example.playlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    Button btnSearch, btnStop;

    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        listView = (ListView) findViewById(R.id.lvSongs);
        dataModels = new ArrayList<>();

        dataModels.add(new DataModel("Cruel Summer", "Taylor Swift", "02:59"));
        dataModels.add(new DataModel("Don't Blame Me", "Taylor Swift", "03:56"));
        dataModels.add(new DataModel("As It Was", "Harry Styles", "02:45"));

        adapter = new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnStop = (Button) findViewById(R.id.btnStopMusic);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(PlaylistActivity.this, MusicService.class));
            }
        });
    }
}