package com.example.huongthutran.sunmusic.ui.Fragment.Album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Adapter.PlaylistAdapter;
import com.example.huongthutran.sunmusic.Adapter.SongsAlbumAdapter;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.Model.SongAlbumViewHolder;
import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;

import java.util.List;

public class FragmentAlbumDetail extends android.support.v4.app.Fragment {
    View rootView;
    public static transient PlayListSong album=new PlayListSong();
    RecyclerView recyclerView;
    AppCompatButton btnPlayAll;
    private SongsAlbumAdapter playlistAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.frament_listsong_album, container, false);
        //lấy album bên playlstView Holder
        album= PlayListViewHolder.p;
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView=rootView.findViewById(R.id.recyclerViewSongInAlBum);
        recyclerView.setLayoutManager(llm);
        playlistAdapter=new SongsAlbumAdapter(getContext(),album.getSongs());
        recyclerView.setAdapter(playlistAdapter);
        btnPlayAll=rootView.findViewById(R.id.btnplayall);
        //khi nhấn nge tất cả
        btnPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xóa list đang nghe cũ
                PlayerHelper.getInstance().songList.clear();
                //set tên
                MainActivity.tvNameSinggerPlaying.setText((album.getSongs().get(0).getSinggerName()));
                MainActivity.tvNameSongPlaying.setText((album.getSongs().get(0).getSong_name()));
                MainActivity.imgIconPlay.setImageResource(R.drawable.pause_white);
                //chạy bài hát đầu tiên trước
                PlayerHelper.getInstance().play(album.getSongs().get(0));
                //add list
                PlayerHelper.getInstance().addList(album.getSongs());

                MainActivity.imgIconPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(PlayerHelper.getInstance().getState()== State.PLAY){
                            PlayerHelper.getInstance().onPause();
                        } else if(PlayerHelper.getInstance().getState()==State.PAUSE){
                            PlayerHelper.getInstance().onResume();
                        }
                    }
                });
            }
        });
        return rootView;
    }
}
