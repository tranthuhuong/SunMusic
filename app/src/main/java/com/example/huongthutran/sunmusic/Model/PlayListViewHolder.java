package com.example.huongthutran.sunmusic.Model;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Fragment.Album.FragmentAlbumDetail;

import java.io.Serializable;

public class PlayListViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNamePlaylist;
    private PlayListSong playListSong=new PlayListSong();
    public static transient  PlayListSong p=new PlayListSong();
    public static transient  Bundle bundle=new Bundle();

    ImageView imgv;
    LinearLayout lnBG;
    public static  int k;
    private Context context;
    public PlayListViewHolder(View itemView) {
        super(itemView);
        tvNamePlaylist=itemView.findViewById(R.id.tvNamePlayList);
        imgv=itemView.findViewById(R.id.imageListPlay);
        lnBG=itemView.findViewById(R.id.bg_list);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p=playListSong;
                FragmentAlbumDetail fragment=new FragmentAlbumDetail();
                //fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentlayout, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        });

    }


    public void setData(PlayListSong s, int p,Context context){
        //img.setImageResource(img);
        this.context=context;
        Drawable bitmapDrawable = null;
        playListSong=s;
        tvNamePlaylist.setText(s.getName_playlist());
        switch (p){
            case 0:lnBG.setBackgroundResource(R.drawable.bg1); break;
            case 1:lnBG.setBackgroundResource(R.drawable.bg3); break;
            case 2:lnBG.setBackgroundResource(R.drawable.bg4); break;
        }

        new DownloadImageTask(imgv)
                .execute(s.getImage());
    }
}
