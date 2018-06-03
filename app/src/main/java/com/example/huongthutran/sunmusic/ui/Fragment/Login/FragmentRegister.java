package com.example.huongthutran.sunmusic.ui.Fragment.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.LoginActivity;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBack;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.NetWork.Post;
import com.example.huongthutran.sunmusic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentRegister extends android.support.v4.app.Fragment implements CallBackData{
    View rootView;
    Button btnRegister;
    EditText edtUserName,edtPass,edtRePass,edtEmail,edtName;

    int idUser;
    static FragmentRegister kt=new FragmentRegister();
    private List<HttpParam> ls=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_register, container, false);
        btnRegister=rootView.findViewById(R.id.btnRegister);
        edtPass=rootView.findViewById(R.id.edtPassword);
        edtUserName=rootView.findViewById(R.id.edtUserName);
        edtRePass=rootView.findViewById(R.id.edtRePassword);
        edtEmail=rootView.findViewById(R.id.edtEmail);
        edtName=rootView.findViewById(R.id.edtName);
        CallApi.getInstance().SetcallBack(this);
        LoginActivity.tvdangki.setText("Đăng nhập tài khoản đã có");
        LoginActivity.tvdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(FragmentLogin.newInstance());
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edtEmail.getText().toString().trim();
                String userName=edtUserName.getText().toString().trim();
                String pass=edtPass.getText().toString().trim();
                String rePass=edtPass.getText().toString().trim();
                String name=edtName.getText().toString().trim();
                if(email.equals("")||userName.equals("")||pass.equals("")||rePass.equals("")){
                    Toast.makeText(getContext(),"Bạn Chưa nhập đủ thông tin",Toast.LENGTH_LONG).show();
                } else if(!rePass.equals(pass)){
                    Toast.makeText(getContext(),"2 mật khẩu không khớp",Toast.LENGTH_LONG).show();
                } else {

                    JSONObject json = new JSONObject();
                    try {
                        json.put("uid", userName);
                        json.put("password", pass);
                        json.put("name", name);
                        json.put("image", "");
                        json.put("jurisdiction", 0);
                        json.put("email", email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CallApi.getInstance().CallapiServer(ApiType.POST_USER, null, json);
                }

        }
        });
        return rootView;
    }
    public static FragmentRegister newInstance() {
        if(kt == null){
            kt = new FragmentRegister();
        }
        return kt;
    }
    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.POST_USER) {
            try {
                JSONObject jsonObject =new JSONObject(json);
                String username=jsonObject.getString("uid");
                JSONObject jsonListLove = new JSONObject();
                try {
                    jsonListLove.put("uid", username);
                    jsonListLove.put("playlist_id", randomNumber());
                    jsonListLove.put("name_playlist", "love list");
                    jsonListLove.put("style", 1);
                    jsonListLove.put("image", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(),"Đăng ký thành công",Toast.LENGTH_LONG).show();
                CallApi.getInstance().CallapiServer(ApiType.POST_PLAYLIST, null, jsonListLove);
                setFragment(FragmentLogin.newInstance());


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),json,Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),"UserName Đã tồn tại",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void setFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = ((LoginActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.idFragmentLogin, fragment);
        transaction.commit();
    }
    public int randomNumber() {
        Random random = new Random();
        String number = "1234567890987654321567892345658437346983726423895741";
        int leng = number.length();
        String ran = "";
        for (int i = 0; i < 4; i++) {
            ran += number.charAt(random.nextInt(leng - 1));
        }
        return Integer.parseInt(ran);
    }
}
