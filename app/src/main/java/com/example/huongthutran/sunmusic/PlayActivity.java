package com.example.huongthutran.sunmusic;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.util.LinkedList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener{
    private Handler handler=new Handler();
    private ImageView imgSong_bg;
    private ImageView imgShowCurrentList;
    private ImageView btnPre;
    private ImageView btnNext;
    private static ImageView btnPlayPause;
    private static TextView tvSinggerName, tvSongName;
//    private SeekBar seekBar;

    int mediaFileLengthInMilliseconds=0;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        /*seekBar.setMax(99);
        seekBar.setOnTouchListener(this);
        updatePercentValue(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updatePercentValue(seekBar.getProgress());
                primarySeekBarProgressUpdater();
               // Toast.makeText(PlayActivity.this, "stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handleSeekBar();
                //primarySeekBarProgressUpdater();

              //  Toast.makeText(PlayActivity.this, "started...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                updatePercentValue(seekBar.getProgress());
                //primarySeekBarProgressUpdater();
               // Toast.makeText(PlayActivity.this, "started...", Toast.LENGTH_SHORT).show();
            }
        });
*/

    }

    private void primarySeekBarProgressUpdater() {
        mediaFileLengthInMilliseconds=PlayerHelper.getInstance().getMediaPlayer().getDuration();
       // seekBar.setProgress((int)(((float)PlayerHelper.getInstance().getMediaPlayer().getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
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
    private void updatePercentValue(int progressValue) {
      Log.d("test",progressValue + "%");
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    void init(){
        //btnClosePlayer = findViewById(R.id.btnClosePlayer);
        imgSong_bg = findViewById(R.id.imgSong_bg);
        //imgShowCurrentList = findViewById(R.id.currentList);
        btnPlayPause = findViewById(R.id.playPause);
        //btnNext = findViewById(R.id.nextSong);
        btnPre = findViewById(R.id.btnPre);
        //seekBar=findViewById(R.id.seekBarPlayer);
        tvSongName = findViewById(R.id.tvSongName);
        tvSinggerName = findViewById(R.id.tvSinggerName);
        context = getApplicationContext();

        //showPlayerMore();
        //closePlayer();
        showCurrentSong();
        //nextSong();
        playPause();

       // primarySeekBarProgressUpdater();

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
}
