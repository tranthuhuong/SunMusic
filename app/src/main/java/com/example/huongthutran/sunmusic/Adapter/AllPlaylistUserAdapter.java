package com.example.huongthutran.sunmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Model.PlayListUserViewHolder;
import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.util.List;

public class AllPlaylistUserAdapter extends RecyclerView.Adapter<PlayListUserViewHolder> {
    List<PlayListSong> playListSongs;
    Context context;
    int k=-1;
    Song song;
    int style;//=0 khi hiển trị để nge, = 1 để thêm nhạc vào list
    private LayoutInflater inflater;
    public AllPlaylistUserAdapter(Context context, List<PlayListSong> l, int style, Song song){
        inflater = LayoutInflater.from(context);
        this.song=song;
        this.style=style;
        this.playListSongs = l;
        this.context = context;
    }
    @Override
    public PlayListUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.item_playlist_user,parent,false);

        return new PlayListUserViewHolder(itemview,style,song);
    }

    @Override
    public void onBindViewHolder(PlayListUserViewHolder holder, int position) {
        holder.setData(playListSongs.get(position),context);

    }

    @Override
    public int getItemCount() {
        return playListSongs.size();
    }


}
