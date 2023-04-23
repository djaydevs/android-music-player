package com.example.playlistapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> {
    private final ArrayList<DataModel> dataSet;
    Context mContext;
    MediaPlayer mPlayer;
    int status = 0;

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvArtist;
        TextView tvTime;
        ImageView btnPlay;
        ImageView ivCover;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_layout, data);
        this.dataSet = data;
        this.mContext = context;
    }

//    @Override
//    public void onClick(View view) {
//        int position = (Integer) view.getTag();
//        Object object = getItem(position);
//        DataModel dataModel = (DataModel) object;
//
//        Toast.makeText(view.getContext(), "Playing - " + dataModel.getTitle(), Toast.LENGTH_LONG).show();
//    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataModel dataModel = getItem(position);
        ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout, parent, false);

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.btnPlay = (ImageView) convertView.findViewById(R.id.btnPlaySong);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.albumCover);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        int value;
        viewHolder.tvTitle.setText(dataModel.getTitle());
        viewHolder.tvArtist.setText(dataModel.getArtist());
        viewHolder.tvTime.setText(dataModel.getTime());
        //viewHolder.btnPlay.setOnClickListener(this);
        viewHolder.btnPlay.setTag(position);

        String song = dataModel.getTitle();

        if (song.equals("Cruel Summer")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.lover);
        }
        if (song.equals("Don't Blame Me")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.reputation);
        }
        if (song.equals("As It Was")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.harrys_house);
        }

        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer)view.getTag();
                Object obj = getItem(position);
                DataModel dModel = (DataModel) obj;

                if (dataModel.getTitle().equals("Cruel Summer")) {
                    //viewHolder.ivCover.setBackgroundResource(R.drawable.lover);

                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }

                    mPlayer = MediaPlayer.create(view.getContext(), R.raw.cruel_summer);
                    mPlayer.setLooping(false);
                    mPlayer.start();
                }
                if (dataModel.getTitle().equals("Don't Blame Me")) {
                    //viewHolder.ivCover.setBackgroundResource(R.drawable.reputation);

                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }

                    mPlayer = MediaPlayer.create(view.getContext(), R.raw.dont_blame_me);
                    mPlayer.setLooping(false);
                    mPlayer.start();
                }
                if (dataModel.getTitle().equals("As It Was")) {
                   //viewHolder.ivCover.setBackgroundResource(R.drawable.reputation);

                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }

                    mPlayer = MediaPlayer.create(view.getContext(), R.raw.as_it_was);
                    mPlayer.setLooping(false);
                    mPlayer.start();
                }

                Toast.makeText(view.getContext(), "Playing - " + dataModel.getTitle().toString(), Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }
}
