package com.example.huongthutran.sunmusic.ui.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.ApiType;
import com.example.huongthutran.sunmusic.NetWork.CallApi;
import com.example.huongthutran.sunmusic.NetWork.CallBackData;
import com.example.huongthutran.sunmusic.NetWork.HttpParam;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.Util.PlayerHelper;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.datamodel.Rating;
import com.example.huongthutran.sunmusic.datamodel.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DialogRating implements CallBackData{
    Context context;
    int playlist_id=0;
    Rating rating=new Rating();
    boolean flag=true;//true khi chưa có đánh giá, và ngược lại
    final Dialog alertDialog;
    final EditText edtComment;
    Song song;
    final RatingBar ratingBar;
    public DialogRating(final Context context, final Song song){
        this.context=context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        CallApi.getInstance().SetcallBack(this);
        View dialog_rating = inflater.inflate(R.layout.dialog_rating, null);
        builder.setView(dialog_rating);
        this.song=song;
        final int[] score = {0};
        alertDialog= new Dialog(context);
        alertDialog.setContentView(dialog_rating);
        ratingBar = dialog_rating.findViewById(R.id.ratingBar);
        final Button btnRating=dialog_rating.findViewById(R.id.btnRating);
        edtComment=dialog_rating.findViewById(R.id.edtComment);
        CallApi.getInstance().setU("");
        List<HttpParam> p=new ArrayList<>();
        p.add(new HttpParam("userid",MainActivity.user.getUid()));
        p.add(new HttpParam("songid",song.getSong_id()));
        CallApi.getInstance().CallapiServer(ApiType.GET_RATING, p, null);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                score[0] = (int) (v*10/10);
            }
        });
        setButton();
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    CallApi.getInstance().setU("");
                    JSONObject jsonRating = new JSONObject();
                    try {
                        jsonRating.put("uid",MainActivity.user.getUid() );
                        jsonRating.put("song_id", PlayerHelper.getInstance().getCurrentSong().getSong_id());
                        jsonRating.put("start", score[0]);
                        jsonRating.put("comment", edtComment.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CallApi.getInstance().CallapiServer(ApiType.POST_RATING, null, jsonRating);
                    alertDialog.dismiss();
                } else {
                    CallApi.getInstance().setU(""+ PlayerHelper.getInstance().getCurrentSong().getSong_id());
                    JSONObject jsonRating = new JSONObject();
                    try {
                        jsonRating.put("uid",MainActivity.user.getUid() );
                        jsonRating.put("song_id", PlayerHelper.getInstance().getCurrentSong().getSong_id());
                        jsonRating.put("start", score[0]);
                        jsonRating.put("comment", edtComment.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    CallApi.getInstance().CallapiServer(ApiType.PUT_RATING, null, jsonRating);
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.show();

    }
    void setButton(){

    }
    @Override
    public void Callback(ApiType apiType, String json) {

        if (apiType == ApiType.POST_RATING ||apiType == ApiType.PUT_RATING) {
            try {
                JSONObject jsonObject =new JSONObject(json);
                rating.setComment(jsonObject.getString("comment"));
                rating.setStart(jsonObject.getInt("start"));
                rating.setUid(jsonObject.getString("uid"));
                flag=false;
                Toast.makeText(context,"Đánh giá thành công",Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
                flag=true;
                Toast.makeText(context,"Đánh giá không thành công",Toast.LENGTH_LONG).show();
            }
        }
        if(apiType==ApiType.GET_RATING){
            try {
                JSONArray listJSON = new JSONArray(json);
                JSONObject jsonObject;
                for (int i=0;i<listJSON.length();i++){
                    jsonObject=listJSON.getJSONObject(i);
                    rating.setComment(jsonObject.getString("comment"));
                    rating.setStart(jsonObject.getInt("start"));
                    rating.setUid(jsonObject.getString("uid"));
                    flag=false;
                }
                ratingBar.setRating((float) rating.getStart());
                edtComment.setText(rating.getComment());

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Bạn Chưa có đánh giá nào",Toast.LENGTH_LONG).show();

            }
        }
    }
}
