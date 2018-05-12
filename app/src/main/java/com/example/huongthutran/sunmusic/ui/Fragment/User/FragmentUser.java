package com.example.huongthutran.sunmusic.ui.Fragment.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.SongsAlbumAdapter;
import com.example.huongthutran.sunmusic.LoginActivity;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.Model.PlayListViewHolder;
import com.example.huongthutran.sunmusic.Model.State;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.User;
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentUser extends android.support.v4.app.Fragment implements CallBackData{
    View rootView;
    Button btnLogin;
    TextView tvUserName,tvUserJur;
    LinearLayout lnPlaylistLike, lnPlayList;

    static FragmentUser kt=new FragmentUser();
    private List<HttpParam> ls=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_user, container, false);
        CallApi.getInstance().SetcallBack(this);
        tvUserName=rootView.findViewById(R.id.tvNameUser);
        tvUserJur=rootView.findViewById(R.id.tvJurisdictionUser);
        lnPlaylistLike=rootView.findViewById(R.id.lnPlaylistLike);
        lnPlayList=rootView.findViewById(R.id.lnPlaylist);
        tvUserName.setText(MainActivity.user.getName());
        if(MainActivity.user.getJurisdiction()==1){
            tvUserJur.setText("Quản trị");
        } else tvUserJur.setText("Thành viên");
        lnPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lnPlaylistLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
