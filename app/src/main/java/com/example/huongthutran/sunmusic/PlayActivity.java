package com.example.huongthutran.sunmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.AllPlaylistUserAdapter;
import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Dialog.DialogRating;
import com.example.huongthutran.sunmusic.ui.Dialog.Dialog_add_newPlaylist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener,CallBackData{
    private static Handler handler=new Handler();
    private ImageView imgSong_bg;
    private ImageView imgShowCurrentList;
    private ImageView btnPre;
    private ImageView btnNext;
    private ImageView btnRaiting,btnAddPlaylist,btnAddToListLove;
    private static ImageView btnPlayPause;
    private static TextView tvSinggerName, tvSongName;
    public static SeekBar seekBar;
    private static int timeLength;
    int mediaFileLengthInMilliseconds=0;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        seekBar=findViewById(R.id.seekBarPlayer);
        init();
        CallApi.getInstance().SetcallBack(this);
    }


    private void primarySeekBarProgressUpdater() {
        mediaFileLengthInMilliseconds=PlayerHelper.getInstance().getMediaPlayer().getDuration();

        if (PlayerHelper.getInstance().getState()==State.PLAY) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }
    }
    private void handleSeekBar(){
        handler = new Handler();
        primarySeekBarProgressUpdater();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    void init(){
        imgSong_bg = findViewById(R.id.imgSong_bg);
        btnPlayPause = findViewById(R.id.playPause);
        btnNext = findViewById(R.id.btnNext);
        btnPre = findViewById(R.id.btnPre);
        btnRaiting=findViewById(R.id.btnRating);
        tvSongName = findViewById(R.id.tvSongName);
        tvSinggerName = findViewById(R.id.tvSinggerName);
        context = getApplicationContext();

        btnAddPlaylist=findViewById(R.id.btnAddPlaylist);
        btnAddToListLove=findViewById(R.id.btnAddToListLove);
        showCurrentSong();
        nextSong();
        playPause();
        preSong();
        addPlaylist();
        addToListLove();
        btnRaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogRating(PlayActivity.this,PlayerHelper.getInstance().getCurrentSong());
                }
        });

    }
    private static Runnable updateSeekbar = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(PlayerHelper.getInstanse().getMediaPlayer().getCurrentPosition());
           // tvCurrentTime.setText(TimeHelper.getTime(MediaPlayerHelper.getInstanse().getMediaPlayer().getCurrentPosition()));
            handler.postDelayed(this, 1000);
        }
    };
    public static void initSeekBar() {
        timeLength = PlayerHelper.getInstanse().getMediaPlayer().getDuration();
        seekBar.setMax(timeLength);
       // tvTotalTime.setText(TimeHelper.getTime(timeLength));
        //tvCurrentTime.setText(TimeHelper.getTime(MediaPlayerHelper.getInstanse().getMediaPlayer().getCurrentPosition()));
        handler.post(updateSeekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                PlayerHelper.getInstanse().seekToTime(progress);
               // tvCurrentTime.setText(TimeHelper.getTime(MediaPlayerHelper.getInstanse().getMediaPlayer().getCurrentPosition()));
            }
        });
    }
    public void addToListLove(){
        btnAddToListLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.love.getSongs().add(PlayerHelper.getInstance().getCurrentSong());
                // Toast.makeText(context,"Đã thêm vào danh sách yêu thích",Toast.LENGTH_LONG).show();
                CallApi.getInstance().setU("");
                JSONObject jsonSong = new JSONObject();
                try {
                    jsonSong.put("playlist_id",MainActivity.love.getPlaylist_id() );
                    jsonSong.put("song_id", PlayerHelper.getInstance().getCurrentSong().getSong_id());
                    jsonSong.put("timead", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CallApi.getInstance().CallapiServer(ApiType.POST_SONGLIST, null, jsonSong);
            }
        });
    }
    public void addPlaylist(){
        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                LayoutInflater inflater = LayoutInflater.from(PlayActivity.this);

                View dialogPlaylist = inflater.inflate(R.layout.dialog_all_playlist, null);
                builder.setView(dialogPlaylist);

                final AlertDialog alertDialog = builder.create();
                RecyclerView listView = dialogPlaylist.findViewById(R.id.listViewAllList);
                LinearLayout ln_addPlaylist=dialogPlaylist.findViewById(R.id.ln_addPlaylist);
                LinearLayoutManager llm = new LinearLayoutManager(PlayActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                listView.setLayoutManager(llm);
                AllPlaylistUserAdapter playlistAdapter;
                playlistAdapter= new AllPlaylistUserAdapter(context,MainActivity.playListSongs,0,PlayerHelper.getInstance().getCurrentSong());
                listView.setAdapter(playlistAdapter);
                ln_addPlaylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog_add_newPlaylist dialog_add_newPlaylist=new Dialog_add_newPlaylist(PlayActivity.this,PlayerHelper.getInstance().getCurrentSong());
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
        });
    }

    public static void showCurrentSong(){

        if(PlayerHelper.getInstance().getSizeList() > 0){
            changeStatePlayPause();
            btnPlayPause.setImageResource(R.drawable.pause_white);
            tvSinggerName.setText(PlayerHelper.getInstance().getCurrentSong().getSinggerName());
            tvSongName.setText(PlayerHelper.getInstance().getCurrentSong().getSong_name());

        }
    }

    private static void changeStatePlayPause(){
        if(PlayerHelper.getInstance().getState() == State.PLAY){
            initSeekBar();
            btnPlayPause.setImageResource(R.drawable.pause_white);
        }else if(PlayerHelper.getInstance().getState() == State.PAUSE){
            btnPlayPause.setImageResource(R.drawable.play_white);
        }
    }
    private void playPause(){

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayerHelper.getInstance().getState() == State.PLAY){
                    PlayerHelper.getInstance().onPause();
                    changeStatePlayPause();
                }else if(PlayerHelper.getInstance().getState() == State.PAUSE){
                    PlayerHelper.getInstance().onResume();
                    changeStatePlayPause();
                    handleSeekBar();
                }
            }
        });
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (view.getId() == R.id.seekBarPlayer) {
          primarySeekBarProgressUpdater();
           // updateSeekBar();
            // Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (PlayerHelper.getInstance().getState()==State.PLAY) {
                SeekBar sb = (SeekBar) view;

                int playPositionInMillisecconds = (PlayerHelper.getInstance().getMediaPlayer().getDuration() / 100) * sb.getProgress();
                PlayerHelper.getInstance().getMediaPlayer().seekTo(playPositionInMillisecconds);

            }
            //Toast.makeText(PlayActivity.this,"keos",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    void nextSong(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PlayerHelper.getInstance().getSizeList() <= 1){
                    Toast.makeText(getApplicationContext(), "Danh sách chỉ chứa 1 bài.", Toast.LENGTH_SHORT).show();
                }else {
                    PlayerHelper.getInstance().onNext();
                    showCurrentSong();
                }
            }
        });
    }
    void preSong(){
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PlayerHelper.getInstance().getPositionSong() <= 0){
                    Toast.makeText(getApplicationContext(), "Không có bài phía trước", Toast.LENGTH_SHORT).show();
                }else {
                    PlayerHelper.getInstance().onPre();
                    showCurrentSong();
                }
            }
        });
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
