package com.example.huongthutran.sunmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Model.SongAlbumViewHolder;
import com.example.huongthutran.sunmusic.Model.SongViewHolder;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.util.List;

public class SongsAlbumAdapter extends RecyclerView.Adapter<SongAlbumViewHolder>{
    List<Song> songs;
    Context context;
    private LayoutInflater inflater;
    public SongsAlbumAdapter(Context context, List<Song> l){
        inflater = LayoutInflater.from(context);
        this.songs = l;
        this.context = context;

    }
    @Override
    public SongAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.item_song_album,parent,false);

        return new SongAlbumViewHolder(itemview,context);
    }

    @Override
    public void onBindViewHolder(SongAlbumViewHolder holder, int position) {
        holder.setData(songs.get(position),position+1);
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

}
