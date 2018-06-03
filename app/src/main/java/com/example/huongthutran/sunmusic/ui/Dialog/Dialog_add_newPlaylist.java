package com.example.huongthutran.sunmusic.ui.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.Adapter.AllPlaylistUserAdapter;
import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.Song;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dialog_add_newPlaylist implements CallBackData{
    Context context;
    int playlist_id=0;
    Song song;
    public Dialog_add_newPlaylist(Context context, final Song song){
        this.context=context;
        @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater inflater = LayoutInflater.from(context);
        CallApi.getInstance().SetcallBack(this);
        View dialogNewP = inflater.inflate(R.layout.dialog_new_playlist, null);
        builder.setView(dialogNewP);
        this.song=song;
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window dialogWindow = alertDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0; // The new position of the X coordinates
        lp.y = 28; // The new position of the Y coordinates
        dialogWindow.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        dialogWindow.setAttributes(lp);

        final Button btnAdd = dialogNewP.findViewById(R.id.btnYes);
        Button btnCancel=dialogNewP.findViewById(R.id.btnCancel);
        final EditText edt=dialogNewP.findViewById(R.id.edtName_Playlist);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePlaylist=edt.getText().toString();
                JSONObject jsonListLove = new JSONObject();
                try {
                    jsonListLove.put("uid", MainActivity.user.getUid());
                    jsonListLove.put("playlist_id", randomNumber());
                    jsonListLove.put("name_playlist", namePlaylist);
                    jsonListLove.put("style", 0);
                    jsonListLove.put("image", song.getImage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CallApi.getInstance().CallapiServer(ApiType.POST_PLAYLIST, null, jsonListLove);
                alertDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();


    }

    @Override
    public void Callback(ApiType apiType, String json) {
        if (apiType == ApiType.POST_PLAYLIST) {
            try {
                JSONObject jsonObject =new JSONObject(json);
                playlist_id=jsonObject.getInt("playlist_id");
                //sau khi tạo playlisy thì thêm bài đó vào
                CallApi.getInstance().setU("");
                JSONObject jsonSong = new JSONObject();
                try {
                    jsonSong.put("playlist_id",playlist_id );
                    jsonSong.put("song_id", song.getSong_id());
                    jsonSong.put("timead", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CallApi.getInstance().CallapiServer(ApiType.POST_SONGLIST, null, jsonSong);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Tạo không thành công",Toast.LENGTH_LONG).show();
            }
        }
        if (apiType == ApiType.POST_SONGLIST) {
            try {
                JSONObject jsonObject =new JSONObject(json);
                int playlist_id=jsonObject.getInt("playlist_id");
                List<HttpParam> l=new ArrayList<>();
                l.add(new HttpParam("userid",MainActivity.user.getUid()));
                CallApi.getInstance().CallapiServer(ApiType.GET_PLAYLIST, l, null);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Bài Hát đã có trong danh sách",Toast.LENGTH_LONG).show();
            }
        }
        if(apiType == ApiType.GET_PLAYLIST){
            try {
                MainActivity.playListSongs=MainActivity.parseListUser(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,"Thêm Thành công",Toast.LENGTH_LONG).show();
        }
    }
    public static int randomNumber() {
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
