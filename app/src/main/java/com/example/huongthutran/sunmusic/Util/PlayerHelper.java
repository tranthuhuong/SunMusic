package com.example.huongthutran.sunmusic.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.PlayActivity;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHelper {
    private static PlayerHelper instanse = null;
    private MediaPlayer mediaPlayer;
    public List<Song> songList;
    private String state;
    private Song currentSong;
    Context context;

    public static PlayerHelper getInstance() {
        if (instanse == null) {

            instanse = new PlayerHelper();
        }
        return instanse;
    }

    private PlayerHelper() {
        mediaPlayer = new MediaPlayer();
        songList = new ArrayList<>();
        state = State.STOP;
        currentSong = null;
    }

    public void play(final Song song){
        onStop();
        mediaPlayer.reset();
        MainActivity.lnplayMusic.setVisibility(View.VISIBLE);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        addSong(song);
        currentSong = song;

        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    mediaPlayer.setDataSource(strings[0]);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {

                        }
                    });
                    mediaPlayer.start();
                    state = State.PLAY;
                }
                catch (IllegalStateException e) {
                    e.printStackTrace();
                    Log.d("loi",e.toString()+"");
                    mediaPlayer.release();
                    state = State.STOP;
                    currentSong = null;

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d("loi",e.toString()+"");
                    mediaPlayer.release();

                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                state = State.PLAY;
                mediaPlayer.start();


            }
        };
        //truyền vào link nhạc
        task.execute(song.getLink());


    }

    public void onPause() {
        if (state == State.PLAY) {
            mediaPlayer.pause();
            state = State.PAUSE;
           MainActivity.imgIconPlay.setImageResource(R.drawable.play_white);
        }
    }

    public void clearList() {
        songList.clear();
    }
    public void onResume() {
        if (state == State.PAUSE) {
            mediaPlayer.start();
            state = State.PLAY;
            MainActivity.imgIconPlay.setImageResource(R.drawable.pause_white);
        }
    }

    public void onStop() {
        mediaPlayer.stop();
        state = State.STOP;
        currentSong = null;
    }

    public void onNext(){
        int position = songList.indexOf(currentSong);
        if (position + 1 < songList.size()) {
            currentSong = songList.get(position + 1);
            play(currentSong);
        } else {
            currentSong = songList.get(0);
            play(currentSong);
        }
//        PlayActivity.showCurrentSong();
        MainActivity.tvNameSongPlaying.setText(currentSong.getSong_name());
        MainActivity.tvNameSinggerPlaying.setText(currentSong.getSinggerName());

    }

    public void removeSong(int id) {

    }

    public boolean addList(List<Song> songs) {
        //nếu chưa tồn tại thì add
        for(int i=0;i<songs.size();i++){
            if (!songList.contains(songs.get(i))) {
                songList.add(songs.get(i));

            }
        }
        return true;
    }
    public boolean addSong(Song song) {
        //nếu chưa tồn tại thì add
        if (!songList.contains(song)) {
            songList.add(song);
            return true;
        } return false;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public static PlayerHelper getInstanse() {
        return instanse;
    }

    public static void setInstanse(PlayerHelper instanse) {
        PlayerHelper.instanse = instanse;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public int getSizeList() {
        return songList.size();
    }
}
