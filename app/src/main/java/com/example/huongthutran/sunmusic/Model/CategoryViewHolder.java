package com.example.huongthutran.sunmusic.Model;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huongthutran.sunmusic.MainActivity;
import com.example.huongthutran.sunmusic.NetWork.DownloadImageTask;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.CategorySong;
import com.example.huongthutran.sunmusic.datamodel.PlayListSong;
import com.example.huongthutran.sunmusic.ui.Fragment.Album.FragmentAlbumDetail;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNamePlaylist;
    private CategorySong categorySong=new CategorySong();
    public static transient  PlayListSong p=new PlayListSong();
    public static transient  Bundle bundle=new Bundle();

    ImageView imgv;
    LinearLayout lnBG;
    public static  int k;
    private Context context;
    public CategoryViewHolder(View itemView) {
        super(itemView);
        imgv=itemView.findViewById(R.id.imageCategory);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //p=playListSong;
                //bundle.putSerializable("ALBUM", (Serializable) playListSong);
              /*  FragmentAlbumDetail fragment=new FragmentAlbumDetail();
                //fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentlayout, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();*/
            }
        });

    }


    public void setData(CategorySong s, Context context){
        //img.setImageResource(img);
        this.context=context;
        Drawable bitmapDrawable = null;
        categorySong=s;
        Log.d("imge__",s.getImage());
        new DownloadImageTask(imgv)
                .execute(s.getImage());
    }
}
