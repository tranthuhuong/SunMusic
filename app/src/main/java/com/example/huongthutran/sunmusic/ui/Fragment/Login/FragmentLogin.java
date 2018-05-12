package com.example.huongthutran.sunmusic.ui.Fragment.Login;

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
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentLogin extends android.support.v4.app.Fragment implements CallBackData{
    View rootView;
    Button btnLogin;
    EditText edtUserName,edtPass;

    String idUser;
    static FragmentLogin kt=new FragmentLogin();
    private List<HttpParam> ls=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin=rootView.findViewById(R.id.btnLogin);
        edtPass=rootView.findViewById(R.id.edtpassword);
        edtUserName=rootView.findViewById(R.id.edtUserName);
        CallApi.getInstance().SetcallBack(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ls.clear();
                HttpParam httpParam1=new HttpParam();
                httpParam1.value= edtUserName.getText().toString()+"";
                httpParam1.param="username";
                ls.add(httpParam1);
                HttpParam httpParam2=new HttpParam();
                httpParam2.value=edtPass.getText().toString()+"" ;
                httpParam2.param="pass";
                ls.add(httpParam2);
                CallApi.getInstance().CallapiServer(ApiType.GET_USER, ls, null);
            }
        });
        return rootView;
    }
    public static FragmentLogin newInstance() {
        if(kt == null){
            kt = new FragmentLogin();
        }
        return kt;
    }
    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.GET_USER) {
            try {
                JSONArray listJSON = new JSONArray(json);
                JSONObject jsonObject;
                if(listJSON.isNull(0)){
                    Toast.makeText(getContext(),"Thông tin chưa đúng",Toast.LENGTH_LONG);
                }
                jsonObject=listJSON.getJSONObject(0);

                idUser=jsonObject.getString("uid");
                Intent intent = new Intent(getContext(),MainActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("IDUSER", idUser);
                intent.putExtra("USERLOGIN", mBundle);
                startActivity(intent);
                getActivity().finish();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"Thông tin chưa đúng",Toast.LENGTH_LONG).show();
            }
        }
    }

}
