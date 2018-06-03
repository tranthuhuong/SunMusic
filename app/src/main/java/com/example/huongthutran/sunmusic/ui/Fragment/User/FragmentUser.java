package com.example.huongthutran.sunmusic.ui.Fragment.User;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.AllPlaylistUserAdapter;
import com.example.huongthutran.sunmusic.Adapter.PlaylistAdapter;
import com.example.huongthutran.sunmusic.Adapter.SongsAdapter;
import com.example.huongthutran.sunmusic.Adapter.SongsAlbumAdapter;
import com.example.huongthutran.sunmusic.LoginActivity;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.User;
import com.example.huongthutran.sunmusic.ui.Fragment.Album.FragmentPlaylistLove;
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;
import com.example.huongthutran.sunmusic.ui.Fragment.Playlist.FragmentPlaylistDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentUser extends android.support.v4.app.Fragment implements CallBackData{
    View rootView;
    TextView tvUserName,tvUserJur,tvLogout;
    LinearLayout lnPlaylistLike, lnPlayList;
    RecyclerView recyclerView_listPlayList;
    ImageView imgUser;
    static FragmentUser kt=new FragmentUser();
    private AllPlaylistUserAdapter playlistAdapter;
    private List<HttpParam> ls=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_user, container, false);
        CallApi.getInstance().SetcallBack(this);
        tvUserName=rootView.findViewById(R.id.tvNameUser);
        imgUser=rootView.findViewById(R.id.imgUser);
        tvUserJur=rootView.findViewById(R.id.tvJurisdictionUser);
        lnPlaylistLike=rootView.findViewById(R.id.lnPlaylistLike);
        lnPlayList=rootView.findViewById(R.id.lnPlaylist);
        recyclerView_listPlayList=rootView.findViewById(R.id.recyclerListUser);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_listPlayList.setLayoutManager(llm);
        tvLogout=rootView.findViewById(R.id.tvLogout);
        tvUserName.setText(MainActivity.user.getName());
        if(MainActivity.user.getJurisdiction()==1){
            tvUserJur.setText("Quản trị");
        } else tvUserJur.setText("Thành viên");
        new DownloadImageTask(imgUser)
                .execute(MainActivity.user.getImage());
        lnPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lnPlaylistLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.love.getSongs().size()==0){
                    Toast.makeText(getContext(),"Chưa có bài nào trong danh sách",Toast.LENGTH_LONG).show();
                } else {
                    FragmentPlaylistLove fragment=new FragmentPlaylistLove();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentlayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.commit();
                }

            }
        });
        playlistAdapter= new AllPlaylistUserAdapter(getContext(),MainActivity.playListSongs,1,null);
        recyclerView_listPlayList.setAdapter(playlistAdapter);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có Muốn thoát không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return rootView;
    }
    public static FragmentUser newInstance() {
        if(kt == null){
            kt = new FragmentUser();
        }
        return kt;
    }
    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.GET_USER) {

        }

    }

}
