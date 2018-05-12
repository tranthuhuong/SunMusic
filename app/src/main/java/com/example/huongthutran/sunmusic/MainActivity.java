package com.example.huongthutran.sunmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.PlaylistAdapter;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.Util.BottomNavigationViewHelper;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.datamodel.User;
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;
import com.example.huongthutran.sunmusic.ui.Fragment.User.FragmentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackData{
    public static Context context;
    public static TextView tvNameSongPlaying,tvNameSinggerPlaying;
    public static ImageView imgIconPlay,imgNext,imgPre;
    public static SeekBar seekBar;
    public static LinearLayout lnplayMusic;
    public String id;
    public PlayListSong love=new PlayListSong();//Danh sách yêu thích
    public List<PlayListSong> playListSongs=new ArrayList<>();

    public static User user=new User();

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                  setFragment(FragmentHome.newInstance());
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(FragmentUser.newInstance());
                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.navigation_search:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        lnplayMusic=findViewById(R.id.playMusic);
        lnplayMusic.setVisibility(View.GONE);
        context = getApplicationContext();
        CallApi.getInstance().SetcallBack(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        tvNameSinggerPlaying=findViewById(R.id.tvNameSinggerPlaying);
        tvNameSongPlaying=findViewById(R.id.tvNameSongPlaying);
        imgIconPlay=findViewById(R.id.imgIconPlay);
        imgNext=findViewById(R.id.imgNext);
        imgPre=findViewById(R.id.imgPre);
        //seekBar=findViewById(R.id.seekBarPlayer);
        Intent loginIntent=getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller=loginIntent.getBundleExtra("USERLOGIN");
        id=packageFromCaller.getString("IDUSER");
        CallApi.getInstance().setU(id);
        CallApi.getInstance().CallapiServer(ApiType.GET_USER, null, null);
        CallApi.getInstance().setU("");
        List<HttpParam> l=new ArrayList<>();
        l.add(new HttpParam("userid",id));
        CallApi.getInstance().CallapiServer(ApiType.GET_PLAYLIST, l, null);
        Toast.makeText(MainActivity.this,"hello "+id,Toast.LENGTH_LONG).show();

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayerHelper.getInstance().getSizeList() <= 1){
                    Toast.makeText(getApplicationContext(), "Danh sách chỉ chứa 1 bài.", Toast.LENGTH_SHORT).show();
                }else {
                    PlayerHelper.getInstance().onNext();
                }
            }
        });
    };
    public void setFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentlayout, fragment);
        transaction.commit();
    }


    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.GET_USER) {
            try {
                user = parseUser(json);
                setFragment(FragmentHome.newInstance());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
        if (apiType == ApiType.GET_PLAYLIST) {
            try {
                parseListUser(json);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
    }
    public User parseUser(String json) throws JSONException {
        User user=new User();
        JSONObject jsonObject = new JSONObject(json);
        user.setName(jsonObject.getString("name"));
        user.setUid(jsonObject.getString("uid"));
        user.setPassword(jsonObject.getString("password"));
        user.setImage(jsonObject.getString("image"));
        user.setEmail(jsonObject.getString("email"));
        user.setJurisdiction(jsonObject.getInt("jurisdiction"));

        return user;
    }
    public void parseListUser(String json) throws JSONException {
        playListSongs.clear();
        JSONArray listJSON = new JSONArray(json);
        JSONObject jsonObject;
        for (int i=0;i<listJSON.length();i++){
            jsonObject=listJSON.getJSONObject(i);
            PlayListSong playListSong=new PlayListSong();
            playListSong.setImage(jsonObject.getString("image"));
            playListSong.setPlaylist_id(jsonObject.getInt("playlist_id"));
            playListSong.setName_playlist(jsonObject.getString("name_playlist"));
            JSONArray songsJson = jsonObject.getJSONArray("songs");
            List<Song> songs = new ArrayList<>();
            for(int j=0;j<songsJson.length();j++){
                Log.d("test","song"+j);
                JSONObject temp = songsJson.getJSONObject(j);
                Song song=new Song();
                song.setSong_id(temp.getString("song_id"));
                song.setSong_name(temp.getString("song_name"));
                song.setLink(temp.getString("link"));
                song.setImage(temp.getString("image"));
                song.setTimeCreate(temp.getString("timeCreate"));
                song.setSinggerId(temp.getInt("singgerID"));
                song.setSinggerName(temp.getString("singgerName"));
                song.setSinggerImage(temp.getString("singgerImage"));
                songs.add(song);
            }
            playListSong.setSongs(songs);
            if(jsonObject.getInt("playlist_id")==0){
                playListSongs.add(playListSong);
            } else love=playListSong;

        }
    }
}
