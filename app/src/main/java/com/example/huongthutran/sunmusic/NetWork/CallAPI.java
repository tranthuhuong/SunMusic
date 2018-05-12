package com.example.huongthutran.sunmusic.NetWork;

import android.os.AsyncTask;
import android.util.Log;

import com.example.huongthutran.sunmusic.Util.Constant;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Huong Thu Tran on 4/27/2018.
 */

public class CallApi {// Gọi Request lên Server
    public CallBackData callBackData;
    public static CallApi callApi;
    private String u="";
    private static String urlSongs = Constant.baseURLFPT + "api/song";
    private static String urlPlayList = Constant.baseURLFPT +"api/PlayList";
    private static String urlCategory = Constant.baseURLFPT +"api/Category";
    private static String urlUser = Constant.baseURLFPT +"api/User";
    public static CallApi getInstance() {
        if (callApi == null) {
            callApi = new CallApi();
        }
        return callApi;
    }
    public void setU(String u){
        this.u=u;
    }
    //Hàm này được View gọi
    public void CallapiServer(ApiType apiType, List<HttpParam> dataHeader, JSONObject dataBody) {
        String urlServer = "";
        String method_all = null;
        Method method = new Method();
        switch (apiType) {
            case POST_PLAYLIST:
                urlServer = urlPlayList;
                method_all = method.methodSplit("POST_PLAYLIST");
                break;
            case GET_SONG:
                urlServer = urlSongs;
                method_all = method.methodSplit("GET_SONG");
                break;
            case GET_PLAYLIST:
                urlServer = urlPlayList;
                method_all = method.methodSplit("GET_PLAYLIST");
                break;
            case GET_CATEGORY:
                urlServer = urlCategory;
                method_all = method.methodSplit("GET_CATEGORY");
                break;
            case GET_PLAYLIST_TOP:
                urlServer = urlPlayList+"?top="+Constant.TOP_PLAYLIST;
                method_all = method.methodSplit("GET_PLAYLIST_TOP");
                break;
            case GET_USER:
                urlServer = urlUser+"/"+u;
                method_all = method.methodSplit("GET_USER");
                break;
            case POST_USER:
                urlServer = urlUser;
                method_all = method.methodSplit("POST_USER");
                break;

        }
        // tạo data nằm trên url gọi lên server
        if (dataHeader != null) {
            urlServer+="?";
            for (HttpParam httpParam : dataHeader) {
                if (dataHeader.indexOf(httpParam) != dataHeader.size() - 1) {
                    urlServer += httpParam.param + "=" + httpParam.value + "&";
                } else {
                    urlServer += httpParam.param + "=" + httpParam.value;
                }
            }
            dataHeader.clear();
        }
        Log.d("Test","urlServer = "+urlServer);
        // tạo data năm trong gói tin HTTP Requests
        new AsyncCallServer(dataBody, method_all, callBackData, apiType).execute(urlServer);
    }
    public class Method {
        ApiType apiType;
        String method;

        public Method() {
        }

        public String methodSplit(String method) {
            String[] split = method.split("_");
            String method_all = split[0];
            return method_all;
        }
    }
    public class AsyncCallServer extends AsyncTask<String, Void, String> {
        public JSONObject body;
        public String method;
        public CallBackData data;
        public ApiType apiType;

        public AsyncCallServer(JSONObject body, String method, CallBackData data, ApiType apiType) {
            this.body = body;
            this.method = method;
            this.data = data;
            this.apiType = apiType;
        }

        @Override
        protected String doInBackground(String... params) {
            String phanhoi = "";
            HttpURLConnection httpURLConnection;
            try {

                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod(method);
                httpURLConnection.addRequestProperty("Content-Type", "application/json");
                httpURLConnection.addRequestProperty("Accept", " text/json");
                httpURLConnection.connect();
                if (body != null) {
                    OutputStream output = httpURLConnection.getOutputStream();
                    output = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    output.write(body.toString().getBytes(Charset.forName("UTF-8")));
                    output.flush();
                }
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream input = httpURLConnection.getInputStream();
                    InputStreamReader inputreader = new InputStreamReader(input);
                    BufferedReader r = new BufferedReader(inputreader);
                    StringBuilder builder = new StringBuilder();
                    String server;

                    while ((server = r.readLine()) != null) {
                        builder.append(server);
                    }
                    phanhoi = builder.toString();
                    Log.d("Test","phanhoi = "+phanhoi);
                    return phanhoi;
                }
            } catch (Exception e) {

                e.printStackTrace();
                Log.d("test",e.toString());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("test",s);
            callBackData.Callback(apiType, s);

        }
    }
    public void SetcallBack(CallBackData callBackData) { // thực thi xong trả kết quả về cho lớp View, cầu nối giữa View-Net
        this.callBackData = callBackData;
    }
   /* public void setU(String u){
        this.u=u;
    }
*/
}
