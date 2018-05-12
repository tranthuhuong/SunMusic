package com.example.huongthutran.sunmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.Model.SongViewHolder;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongViewHolder>{
    List<Song> songs;
    Context context;
    private LayoutInflater inflater;
    public SongsAdapter(Context context, List<Song> l){
        inflater = LayoutInflater.from(context);
        this.songs = l;
        this.context = context;

    }
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.list_item_song,parent,false);

        return new SongViewHolder(itemview,context);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.setData(songs.get(position));
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

}
