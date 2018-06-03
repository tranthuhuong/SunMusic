package com.example.huongthutran.sunmusic.Model;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.LoginActivity;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Fragment.Album.FragmentAlbumDetail;
import com.example.huongthutran.sunmusic.ui.Fragment.Login.FragmentLogin;
import com.example.huongthutran.sunmusic.ui.Fragment.Playlist.FragmentPlaylistDetail;
import com.example.huongthutran.sunmusic.ui.Fragment.User.FragmentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayListUserViewHolder extends RecyclerView.ViewHolder implements CallBackData{
    public TextView tvNamePlaylist,tvAmountSong_item;
    private PlayListSong playListSong=new PlayListSong();
    public static transient  PlayListSong p=new PlayListSong();
    public static transient  Bundle bundle=new Bundle();

    ImageView imgPlaylist;
    public static  int k;
    public int style=0;

    private Context context;
    public PlayListUserViewHolder(final View itemView, final int style, @Nullable final Song song) {
        super(itemView);
        this.style=style;
        CallApi.getInstance().SetcallBack(this);
        tvNamePlaylist=itemView.findViewById(R.id.tvNamePlaylist_item);
        imgPlaylist=itemView.findViewById(R.id.imgPlaylist);
        tvAmountSong_item=itemView.findViewById(R.id.tvAmountSong_item);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có Muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CallApi.getInstance().setU(playListSong.getPlaylist_id()+"");
                        CallApi.getInstance().CallapiServer(ApiType.DELETE_PLAYLIST, null, null);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style==1){//=0 khi hiển trị để nge, = 1 để thêm nhạc vào list
                    p=playListSong;
                    FragmentPlaylistDetail fragment=new FragmentPlaylistDetail();
                    android.support.v4.app.FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentlayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.commit();
                }else {
                    CallApi.getInstance().setU("");
                    JSONObject json = new JSONObject();
                    try {
                        json.put("playlist_id",playListSong.getPlaylist_id() );
                        json.put("song_id", song.getSong_id());
                        json.put("timead", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CallApi.getInstance().CallapiServer(ApiType.POST_SONGLIST, null, json);
                }

            }
        });

    }


    public void setData(PlayListSong s,Context context){
        this.context=context;
        playListSong=s;
        tvNamePlaylist.setText(s.getName_playlist());
        tvAmountSong_item.setText(s.getSongs().size()+" bài hát");
        tvNamePlaylist.setText(s.getName_playlist());
        new DownloadImageTask(imgPlaylist)
                .execute(s.getImage());
    }

    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.POST_SONGLIST) {
            try {
                JSONObject jsonObject =new JSONObject(json);
                int playlist_id=jsonObject.getInt("playlist_id");
                List<HttpParam> l=new ArrayList<>();
                l.add(new HttpParam("userid",MainActivity.user.getUid()));
                CallApi.getInstance().CallapiServer(ApiType.GET_PLAYLIST, l, null);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Bài Hát đã có trong danh sách",Toast.LENGTH_LONG).show();
            }
        }
        if(apiType == ApiType.GET_PLAYLIST){
            try {
                MainActivity.playListSongs=MainActivity.parseListUser(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,"Thêm Thành công",Toast.LENGTH_LONG).show();
        }
        if (apiType == ApiType.DELETE_PLAYLIST) {
            Toast.makeText(context,"Xóa thành công", Toast.LENGTH_LONG).show();
        }
    }
}
