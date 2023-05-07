package com.example.playlistapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> {
    private final ArrayList<DataModel> dataSet;
    private final ArrayList<DataModel> filteredDataSet;
    Context mContext;
    MediaPlayer mPlayer;
    int status = 0;

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_layout, data);
        this.dataSet = data;
        this.mContext = context;

        filteredDataSet = new ArrayList<>(data);
    }

    public void filter(String query) {
        if (query.isEmpty()) {
            filteredDataSet.addAll(dataSet);
        }
        else {
            filteredDataSet.clear();
            for (DataModel item : dataSet) {
                if (item.getTitle().contains(query)) {
                    filteredDataSet.add(item);
                }
                if (item.getArtist().contains(query)) {
                    filteredDataSet.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void stopMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public int getCount() {
        return filteredDataSet.size();
    }

    @Override
    public DataModel getItem(int position) {
        return filteredDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private static class ViewHolder {
        TextView tvTitle;
        TextView tvArtist;
        TextView tvTime;
        ImageView btnPlay;
        ImageView ivCover;
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
        //DataModel item = getItem(position);
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
        if (song.equals("As It Was")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.harrys_house);
        }
        if (song.equals("Falling")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.fine_line);
        }
        if (song.equals("timekeeper")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.portraits);
        }
        if (song.equals("Athena")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.palladium);
        }
        if (song.equals("Mastermind")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.midnights);
        }
        if (song.equals("august")) {
            viewHolder.ivCover.setBackgroundResource(R.drawable.folklore);
        }

        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer)view.getTag();
                Object obj = getItem(position);
                DataModel dModel = (DataModel) obj;

                if (dataModel.getTitle().equals("Cruel Summer")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.cruel_summer);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("Don't Blame Me")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.dont_blame_me);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("As It Was")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.as_it_was);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("Falling")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.falling);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("timekeeper")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.timekeeper);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("Athena")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.athena);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("Mastermind")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.mastermind);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }
                if (dataModel.getTitle().equals("august")) {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    else {
                        mPlayer = MediaPlayer.create(view.getContext(), R.raw.august);
                        mPlayer.setLooping(false);
                        mPlayer.start();
                    }
                }


                Toast.makeText(view.getContext(), "Playing - " + dataModel.getTitle() + " (" + dataModel.getArtist() + ")", Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }
}
