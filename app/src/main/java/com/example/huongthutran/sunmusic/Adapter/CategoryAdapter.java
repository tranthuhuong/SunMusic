package com.example.huongthutran.sunmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huongthutran.sunmusic.Model.CategoryViewHolder;
import com.example.huongthutran.sunmusic.Model.SongViewHolder;
import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.datamodel.CategorySong;
import com.example.huongthutran.sunmusic.datamodel.Song;

import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    List<CategorySong> categorySongs;
    Context context;
    private LayoutInflater inflater;
    public CategoryAdapter(Context context, List<CategorySong> l){
        inflater = LayoutInflater.from(context);
        this.categorySongs = l;
        this.context = context;

    }
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.item_category_home,parent,false);

        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.setData(categorySongs.get(position),context);
    }


    @Override
    public int getItemCount() {
        return categorySongs.size();
    }

}
