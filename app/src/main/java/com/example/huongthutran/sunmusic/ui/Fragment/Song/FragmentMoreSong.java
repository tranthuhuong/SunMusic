package com.example.huongthutran.sunmusic.ui.Fragment.Song;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Adapter.SongsAdapter;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FragmentMoreSong extends android.support.v4.app.Fragment implements CallBackData {
    View rootView;
    RecyclerView recycler_list;
    List<Song> songs=new ArrayList<>();
    SongsAdapter songsAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_list_song, container, false);
        CallApi.getInstance().SetcallBack(this);
        recycler_list=rootView.findViewById(R.id.recyclerListSong);

        CallApi.getInstance().setU("");
        CallApi.getInstance().CallapiServer(ApiType.GET_SONG, null, null);
        return rootView;
    }
    @Override
    public void Callback(ApiType apiType, String json) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        if (apiType == ApiType.GET_SONG) {
            try {
                recycler_list.setLayoutManager(llm);
                songs = FragmentHome.parseSong(json);
                songsAdapter= new SongsAdapter(getContext(),songs);
                recycler_list.setAdapter(songsAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("test callback 1",e.toString());
            }
        }
    }
}
