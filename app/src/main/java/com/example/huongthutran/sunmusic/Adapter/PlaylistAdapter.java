package com.example.huongthutran.sunmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlayListViewHolder>{
    List<PlayListSong> playListSongs;
    Context context;
    int k=-1;
    private LayoutInflater inflater;
    public PlaylistAdapter(Context context, List<PlayListSong> l){
        inflater = LayoutInflater.from(context);
        this.playListSongs = l;
        this.context = context;

    }
    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.item_playlist_home,parent,false);

        return new PlayListViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, int position) {
        holder.setData(playListSongs.get(position),position,context);

    }

    @Override
    public int getItemCount() {
        return playListSongs.size();
    }


}
