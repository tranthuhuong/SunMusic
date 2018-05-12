package com.example.huongthutran.sunmusic.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.Song;

public class SongAlbumViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNameSong,tvNameSingger,tvStt;
    Song song=new Song();
    Context context;
    public SongAlbumViewHolder(View view, final Context context) {
        super(view);
        tvNameSong = view.findViewById(R.id.tvNameSong_item);
        tvNameSingger = view.findViewById(R.id.tvNameSingger_item);
        tvStt = view.findViewById(R.id.sttSong);
        this.context=context;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MainActivity.tvNameSinggerPlaying.setText(song.getSinggerName());
                MainActivity.tvNameSongPlaying.setText(song.getSong_name());
                MainActivity.imgIconPlay.setImageResource(R.drawable.pause_white);
                PlayerHelper.getInstance().play(song);
                MainActivity.imgIconPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(PlayerHelper.getInstance().getState()==State.PLAY){
                            PlayerHelper.getInstance().onPause();
                        } else if(PlayerHelper.getInstance().getState()==State.PAUSE){
                            PlayerHelper.getInstance().onResume();
                        }
                    }
                });
            }
        });



    }
    public void setData(Song s,int i){
        //img.setImageResource(img);
        this.song=s;
        tvNameSong.setText(song.getSong_name());
        tvNameSingger.setText(song.getSinggerName());
        tvStt.setText(i+"");
    }

}
