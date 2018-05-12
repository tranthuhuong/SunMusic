package com.example.huongthutran.sunmusic.NetWork;

import android.os.AsyncTask;
import android.util.Log;

import com.example.huongthutran.sunmusic.Util.Constant;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class Post extends AsyncTask<String, Void, String> {
    CallBack<String> callBack;
    JSONObject data;

    public Post(CallBack<String> callBack, JSONObject data) {
        this.callBack = callBack;
        this.data = data;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = Constant.baseURLFPT + strings[0];
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        OutputStream outStream;
        int c;
        String result = "";
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();//truyen vao method
            httpURLConnection.setRequestMethod("POST");
            Log.d("testhuong",urlString);
            Log.d("testhuong",data.toString());
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept","application/json");
            outStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            outStream.write(data.toString().getBytes(Charset.forName("UTF-8")));
            outStream.flush();
            outStream.close();

            inputStream = httpURLConnection.getInputStream();
            //khác -1 là vẫn còn
            while ((c=inputStream.read()) != -1){
                result+=(char)c;
                Log.d("testhuong", result);
            }

            return result;
        } catch (Exception e) {
            //that bai
            e.printStackTrace();
            Log.d("testhuong", e.toString());

            return "400";
        }
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        Log.i("GET_STATUS", data);
        callBack.onSuccess(data);
    }
}