package com.example.huongthutran.sunmusic.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huong Thu Tran on 4/26/2018.
 */

public class SongAdapter  extends BaseAdapter implements Filterable{
    private Context context;
    private List<Song> songs =new ArrayList<>();

    public SongAdapter(Context context, List<Song> l){
        this.context=context;
        songs=l;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return songs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.list_item_song, null);

        }
        Song song = songs.get(i);
        ImageView imageView = view.findViewById(R.id.imgSong);
        TextView tvNameSong = view.findViewById(R.id.tvNameSong);
        TextView tvNameSingger = view.findViewById(R.id.tvNameSingger);
        tvNameSong.setText(song.getSong_name());
        tvNameSingger.setText(song.getSinggerName());
        Log.d("test callback 1",i+"");
         new DownloadImageTask(imageView)
                .execute(song.getImage());
        return  view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
    /*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }*/
}
