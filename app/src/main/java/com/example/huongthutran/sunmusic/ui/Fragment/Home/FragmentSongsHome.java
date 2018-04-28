package com.example.huongthutran.sunmusic.ui.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huongthutran.sunmusic.Adapter.SongAdapter;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FragmentSongsHome extends Fragment  implements CallBackData{
    private View rootView;
    List<Song> songs=new ArrayList<>();
    SongAdapter songAdapter;
    ListView listViewSong;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_songs_home, container,false);
        listViewSong=rootView.findViewById(R.id.listviewSongs);
        //Gọi API FPT Stopbus
        CallApi.getInstance().SetcallBack(this);
        CallApi.getInstance().CallapiServer(ApiType.GET_SONG, null, null);
        return rootView;
    }

    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.GET_SONG) {
            try {
                songs = parseSong(json);
                Log.d("test2",songs.get(2).getSong_name()+"");
                songAdapter=new SongAdapter(getContext(),songs);
                listViewSong.setAdapter(songAdapter);
                listViewSong.deferNotifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
    }
    public List<Song> parseSong(String json) throws JSONException {
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
            JSONArray busnextJson = jsonObject.getJSONArray("review");
            if(busnextJson.length()>0){
                song.setCountReview(busnextJson.getJSONObject(0).getInt("count"));
                song.setScoreReviews(busnextJson.getJSONObject(0).getInt("score"));
            } else {
                song.setCountReview(0);
                song.setScoreReviews(0);
            }
            songgs.add(song);
        }
        return songgs;
    }
}
