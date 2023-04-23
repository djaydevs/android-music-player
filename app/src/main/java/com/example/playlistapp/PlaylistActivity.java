package com.example.playlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
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
    }
}