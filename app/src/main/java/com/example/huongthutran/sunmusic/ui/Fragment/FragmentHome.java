package com.example.huongthutran.sunmusic.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.CategoryAdapter;
import com.example.huongthutran.sunmusic.Adapter.PlaylistAdapter;
import com.example.huongthutran.sunmusic.Adapter.SongsAdapter;
import com.example.huongthutran.sunmusic.LoginActivity;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.Constant;
import com.example.huongthutran.sunmusic.datamodel.CategorySong;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Fragment.Song.FragmentMoreSong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FragmentHome extends android.support.v4.app.Fragment implements CallBackData{
    int TOP_LISTPLAY=3;
    int TOP_MUSIC=6;
    private View rootView;
    private TextView tvMoreSong;
    private List<HttpParam> ls=new ArrayList<>();
    private List<PlayListSong> playListSongs=new LinkedList<>();
    private List<CategorySong> categorySongs=new LinkedList<>();
    private PlaylistAdapter playlistAdapter;
    private SongsAdapter songsAdapter;
    private RecyclerView recyclerView,listViewSong,recyclerCategory;

    CategoryAdapter categoryAdapter;
    List<Song> songs=new ArrayList<>();


    private static FragmentHome kt = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=rootView.findViewById(R.id.recycler_playList);
        recyclerView.setHasFixedSize(true);
        tvMoreSong=rootView.findViewById(R.id.tvMoreSong);
        CallApi.getInstance().SetcallBack(this);
        CallApi.getInstance().CallapiServer(ApiType.GET_PLAYLIST_TOP, null, null);
        listViewSong=rootView.findViewById(R.id.listviewSongs);
        //Gọi API GET SONG
        CallApi.getInstance().SetcallBack(this);
        ls.clear();
        HttpParam httpParam=new HttpParam();
        httpParam.value= Constant.TOP_SONG+"";
        httpParam.param="top";
        ls.add(httpParam);
        CallApi.getInstance().CallapiServer(ApiType.GET_SONG, ls, null);
        //-----------

        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerCategory=rootView.findViewById(R.id.recycler_category);
        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.setLayoutManager(layoutManager);


        CallApi.getInstance().SetcallBack(this);
        CallApi.getInstance().CallapiServer(ApiType.GET_CATEGORY, null, null);

        tvMoreSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FragmentMoreSong());
            }
        });
        return rootView;
    }
    public void setFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentlayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public static FragmentHome newInstance() {
        if(kt == null){
            kt = new FragmentHome();
        }
        return kt;
    }

    @Override
    public void Callback(ApiType apiType, String json) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        if (apiType == ApiType.GET_PLAYLIST_TOP) {
            try {
                recyclerView.setLayoutManager(llm);
                playListSongs = parseListPlay(json);
                playlistAdapter=new PlaylistAdapter(getContext(),playListSongs);
                recyclerView.setAdapter(playlistAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
        if (apiType == ApiType.GET_SONG) {
            try {
                listViewSong.setLayoutManager(llm);
                songs = parseSong(json);
                songsAdapter= new SongsAdapter(getContext(),songs);
                listViewSong.setAdapter(songsAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
        if (apiType == ApiType.GET_CATEGORY) {
            try {

                categorySongs = parseCategory(json);
                categoryAdapter= new CategoryAdapter(getContext(),categorySongs);
                recyclerCategory.setAdapter(categoryAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
    }
    public static List<Song> parseSong(String json) throws JSONException {
        List<Song> songgs = new LinkedList<>();
        JSONArray listJSON = new JSONArray(json);
        JSONObject jsonObject;
        for (int i=0;i<listJSON.length();i++){
            jsonObject=listJSON.getJSONObject(i);
            Song song=new Song();
            song.setSong_id(jsonObject.getString("song_id"));
            song.setSong_name(jsonObject.getString("song_name"));
            song.setLink(jsonObject.getString("link"));
            song.setImage(jsonObject.getString("image"));
            song.setTimeCreate(jsonObject.getString("timeCreate"));
            song.setSinggerId(jsonObject.getInt("singgerID"));
            song.setSinggerName(jsonObject.getString("singgerName"));
            song.setSinggerImage(jsonObject.getString("singgerImage"));
            song.setAuthorId(jsonObject.getInt("authorID"));
            song.setAuthorName(jsonObject.getString("authorName"));
            song.setAmount_view(jsonObject.getInt("amount_view"));
            JSONArray reviewJson = jsonObject.getJSONArray("review");
            if(reviewJson.length()>0){
                song.setCountReview(reviewJson.getJSONObject(0).getInt("count"));
                song.setScoreReviews(reviewJson.getJSONObject(0).getInt("score"));
            }
            JSONArray addJSON = jsonObject.getJSONArray("amount_add");
            if(addJSON.length()>0){
                song.setCountAddPlayList(addJSON.getJSONObject(0).getInt("count"));
            }
            songgs.add(song);
        }
        return songgs;
    }
    public List<PlayListSong> parseListPlay(String json) throws JSONException {
        List<PlayListSong> plSongs = new LinkedList<>();
        JSONArray listJSON = new JSONArray(json);
        JSONObject jsonObject;
        for (int i=0;i<listJSON.length();i++){
            jsonObject=listJSON.getJSONObject(i);
            PlayListSong playListSong=new PlayListSong();
            playListSong.setImage(jsonObject.getString("image"));
            playListSong.setPlaylist_id(jsonObject.getInt("playlist_id"));
            playListSong.setName_playlist(jsonObject.getString("name_playlist"));
            playListSong.setUid(jsonObject.getString("uid"));
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
            plSongs.add(playListSong);
        }
        return plSongs;
    }
    public List<CategorySong> parseCategory(String json) throws JSONException {
        List<CategorySong> categorySongs = new LinkedList<>();
        JSONArray listJSON = new JSONArray(json);
        JSONObject jsonObject;
        for (int i=0;i<listJSON.length();i++){
            jsonObject=listJSON.getJSONObject(i);
            CategorySong categorySong=new CategorySong();
            categorySong.setImage(jsonObject.getString("image"));

            categorySong.setCategory_id(jsonObject.getInt("category_id"));
            categorySong.setName(jsonObject.getString("name"));

            JSONArray songsJson = jsonObject.getJSONArray("song");
            List<String> songs = new ArrayList<>();
            for(int j=0;j<songsJson.length();j++){
                Log.d("test","song"+j);
                JSONObject temp = songsJson.getJSONObject(j);
                String s;
                s=temp.getString("SongID");
                songs.add(s);
            }
            categorySongs.add(categorySong);
        }
        return categorySongs;
    }
}
