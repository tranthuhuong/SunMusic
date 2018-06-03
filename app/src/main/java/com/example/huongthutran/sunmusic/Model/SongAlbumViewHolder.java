package com.example.huongthutran.sunmusic.Model;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Fragment.Playlist.FragmentPlaylistDetail;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SongAlbumViewHolder extends RecyclerView.ViewHolder implements CallBackData{
    public TextView tvNameSong,tvNameSingger,tvStt;
    Song song=new Song();
    Context context;
    Boolean flag=true;
    String playlist_id;
    public SongAlbumViewHolder(View view, final Context context, final Boolean flag,String id) {
        super(view);
        tvNameSong = view.findViewById(R.id.tvNameSong_item);
        tvNameSingger = view.findViewById(R.id.tvNameSingger_item);
        tvStt = view.findViewById(R.id.sttSong);
        this.context=context;
        this.flag=flag;
        playlist_id=id;
        CallApi.getInstance().SetcallBack(this);
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
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //nếu playlist của user
                if (flag){
                    Dialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có xóa bài hát này trong Playlist ko?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            List<HttpParam> l=new ArrayList<>();
                            l.add(new HttpParam("idSong",song.getSong_id()));
                            CallApi.getInstance().setU(playlist_id);
                            CallApi.getInstance().CallapiServer(ApiType.DELETE_SONGLIST, l, null);
                            FragmentPlaylistDetail.delete(song);
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
                return true;
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

    @Override
    public void Callback(ApiType apiType, String json) {
        if(apiType == ApiType.DELETE_SONGLIST){
            List<HttpParam> l=new ArrayList<>();
            l.add(new HttpParam("userid",MainActivity.user.getUid()));
            CallApi.getInstance().CallapiServer(ApiType.GET_PLAYLIST, l, null);
        }
        if(apiType == ApiType.GET_PLAYLIST){
            try {

                MainActivity.playListSongs=MainActivity.parseListUser(json);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,"Xóa Thành công",Toast.LENGTH_LONG).show();
        }
    }
}
