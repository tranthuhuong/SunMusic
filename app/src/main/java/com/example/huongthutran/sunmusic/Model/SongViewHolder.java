package com.example.huongthutran.sunmusic.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.AllPlaylistUserAdapter;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.PlayActivity;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Dialog.Dialog_add_newPlaylist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongViewHolder extends RecyclerView.ViewHolder implements CallBackData{
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
        CallApi.getInstance().SetcallBack(this);

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
                addFravoriteList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(alertDialog,0);
                    }
                });
                addToPlayListUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(alertDialog,2);
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
        if(dialog != null && style == 0){
            MainActivity.love.getSongs().add(song);
           // Toast.makeText(context,"Đã thêm vào danh sách yêu thích",Toast.LENGTH_LONG).show();
            CallApi.getInstance().setU("");
            JSONObject jsonSong = new JSONObject();
            try {
                jsonSong.put("playlist_id",MainActivity.love.getPlaylist_id() );
                jsonSong.put("song_id", song.getSong_id());
                jsonSong.put("timead", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CallApi.getInstance().CallapiServer(ApiType.POST_SONGLIST, null, jsonSong);
            dialog.dismiss();
        }
        if(dialog != null && style == 2){
            dialog.dismiss();

            @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
            LayoutInflater inflater = LayoutInflater.from(context);

            View dialogPlaylist = inflater.inflate(R.layout.dialog_all_playlist, null);
            builder.setView(dialogPlaylist);

            final AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window dialogWindow = alertDialog.getWindow();

            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0; // The new position of the X coordinates
            lp.y = 28; // The new position of the Y coordinates
            dialogWindow.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
            dialogWindow.setAttributes(lp);

            RecyclerView listView = dialogPlaylist.findViewById(R.id.listViewAllList);
            LinearLayout ln_addPlaylist=dialogPlaylist.findViewById(R.id.ln_addPlaylist);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            listView.setLayoutManager(llm);
            AllPlaylistUserAdapter playlistAdapter;
            playlistAdapter= new AllPlaylistUserAdapter(context,MainActivity.playListSongs,0,song);
            listView.setAdapter(playlistAdapter);
            ln_addPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog_add_newPlaylist dialog_add_newPlaylist=new Dialog_add_newPlaylist(context,song);
                    alertDialog.dismiss();
                }
            });
            listView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();


        }
       // Toast.makeText(context, "Playing", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.POST_SONGLIST) {
            try {
                JSONObject jsonObject =new JSONObject(json);
                int playlist_id=jsonObject.getInt("playlist_id");
                List<HttpParam> l=new ArrayList<>();
                l.add(new HttpParam("userid",MainActivity.user.getUid()));
                l.add(new HttpParam("style","1"));
                CallApi.getInstance().CallapiServer(ApiType.GET_PLAYLIST, l, null);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Bài Hát đã có trong danh sách",Toast.LENGTH_LONG).show();
            }
        }
        if(apiType == ApiType.GET_PLAYLIST){
            try {
                MainActivity.love=MainActivity.parseListLove(json);
                Toast.makeText(context,"Thêm Thành công",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
