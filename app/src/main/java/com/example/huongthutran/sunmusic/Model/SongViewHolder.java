package com.example.huongthutran.sunmusic.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.huongthutran.sunmusic.PlayActivity;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.Song;

public class SongViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNameSong,tvNameSingger ,tvcountPlay,tvcountStart,tvcountAddPlaylist;
    Song song=new Song();
    public static Song nowSong=new Song();
    Context context;
    ImageView imageView;
    public SongViewHolder(View view, final Context context) {
        super(view);
        imageView = view.findViewById(R.id.imgSong);
        tvNameSong = view.findViewById(R.id.tvNameSong);
        tvNameSingger = view.findViewById(R.id.tvNameSingger);
        tvcountPlay = view.findViewById(R.id.tvcountPlay);
        this.context=context;
        tvcountStart = view.findViewById(R.id.tvcountStart);
        tvcountAddPlaylist = view.findViewById(R.id.tvcountAddPlaylist);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerHelper.getInstance().clearList();
                PlayerHelper.getInstance().play(song);
                Intent intent = new Intent(context, PlayActivity.class);
                context.startActivity(intent);
                MainActivity.tvNameSinggerPlaying.setText(song.getSinggerName());
                MainActivity.tvNameSongPlaying.setText(song.getSong_name());
                MainActivity.imgIconPlay.setImageResource(R.drawable.pause_white);

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
                //Intent intent = new Intent(context, PlayActivity.class);
                //context.startActivity(intent);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogOption = inflater.inflate(R.layout.fram_option_song, null);
                builder.setView(dialogOption);

                TextView tvfNameSong, tvfNameSingger;
                ImageView imgfSong;
                LinearLayout addFravoriteList,addToPlayListNow,infoSingger,addToPlayListUser;
                tvfNameSong = dialogOption.findViewById(R.id.tvfNameSong);
                tvfNameSingger = dialogOption.findViewById(R.id.tvfNameSingger);
                imgfSong = dialogOption.findViewById(R.id.imgfSong);
                addFravoriteList = dialogOption.findViewById(R.id.addFravoriteList);
                addToPlayListNow = dialogOption.findViewById(R.id.addToPlayListNow);
                infoSingger = dialogOption.findViewById(R.id.infoSingger);
                addToPlayListUser = dialogOption.findViewById(R.id.addToPlayListUser);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window dialogWindow = alertDialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.x = 0; // The new position of the X coordinates
                lp.y = 28; // The new position of the Y coordinates
                dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                dialogWindow.setAttributes(lp);

                tvfNameSong.setText(song.getSong_name());
                tvfNameSingger.setText(song.getSinggerName());
                new DownloadImageTask(imgfSong)
                        .execute(song.getImage());
                addToPlayListNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(alertDialog,1);
                    }
                });
                alertDialog.show();

                return true;
            }
        });

    }
    public void setData(Song s){
        //img.setImageResource(img);
        this.song=s;
        tvNameSong.setText(song.getSong_name());
        tvNameSingger.setText(song.getSinggerName());
        tvcountPlay.setText("( "+song.getAmount_view()+" )");
        tvcountStart.setText("( "+song.getScoreReviews()+" )");
        tvcountAddPlaylist.setText("( "+song.getCountAddPlayList()+" )");
        new DownloadImageTask(imageView)
                .execute(song.getImage());
    }
    private void handleOnClick(@Nullable final AlertDialog dialog, @Nullable final int style) {
        if(dialog != null && style == 1){

            if(PlayerHelper.getInstance().addSong(song)){
                Toast.makeText(context,"Thêm thành công ",Toast.LENGTH_LONG).show();
            } else  Toast.makeText(context,"Đã tồn tại bài hát",Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
       // Toast.makeText(context, "Playing", Toast.LENGTH_SHORT).show();
    }
}
