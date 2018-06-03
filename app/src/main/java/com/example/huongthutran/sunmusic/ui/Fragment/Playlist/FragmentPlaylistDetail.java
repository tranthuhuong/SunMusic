package com.example.huongthutran.sunmusic.ui.Fragment.Playlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huongthutran.sunmusic.Adapter.AllPlaylistUserAdapter;
import com.example.huongthutran.sunmusic.Adapter.SongsAlbumAdapter;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.Model.PlayListUserViewHolder;
import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.PlayActivity;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;

public class FragmentPlaylistDetail extends android.support.v4.app.Fragment {
    View rootView;
    public static transient PlayListSong album=new PlayListSong();
    RecyclerView recyclerView;
    ImageView imgf_album;
    TextView tvfAlbumName;
    static TextView tvfAmountSong;
    AppCompatButton btnPlayAll;
    private static SongsAlbumAdapter playlistAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.frament_listsong_album, container, false);
        tvfAmountSong=rootView.findViewById(R.id.tvfAmountSong);
        imgf_album=rootView.findViewById(R.id.imgf_album);
        tvfAlbumName=rootView.findViewById(R.id.tvfAlbumName);
        //lấy album bên playlstView Holder
        album= PlayListUserViewHolder.p;
        tvfAlbumName.setText(album.getName_playlist());
        tvfAmountSong.setText(album.getSongs().size() + " bài hát");
        new DownloadImageTask(imgf_album)
                .execute(album.getImage());

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView=rootView.findViewById(R.id.recyclerViewSongInAlBum);
        recyclerView.setLayoutManager(llm);
        playlistAdapter=new SongsAlbumAdapter(getContext(),album.getSongs(),MainActivity.user.getUid().equals(album.getUid()),album.getPlaylist_id()+"");
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
    public static void delete(Song song){
        for(int i=0;i<album.getSongs().size();i++){
            if(song.getSong_id().equals(album.getSongs().get(i).getSong_id())){
                album.getSongs().remove(album.getSongs().get(i));
            }
        }
        tvfAmountSong.setText(album.getSongs().size() + " bài hát");
        playlistAdapter.notifyDataSetChanged();
    }
}
