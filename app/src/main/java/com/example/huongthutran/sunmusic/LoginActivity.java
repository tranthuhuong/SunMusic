package com.example.huongthutran.sunmusic;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.SongsAdapter;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.Util.Constant;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Song;
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;
import com.example.huongthutran.sunmusic.ui.Fragment.Login.FragmentLogin;
import com.example.huongthutran.sunmusic.ui.Fragment.Login.FragmentRegister;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements CallBackData{
    Fragment fragment;
    public static TextView tvdangki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setFragment(FragmentLogin.newInstance());
        tvdangki=findViewById(R.id.tvdangki);
        tvdangki.setText("Đăng ký");
        tvdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(FragmentRegister.newInstance());
            }
        });
    }

    @Override
    public void Callback(ApiType apiType, String json) {

    }
    public void setFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.idFragmentLogin, fragment);
        transaction.commit();
        Log.d("test","2");
    }
}
