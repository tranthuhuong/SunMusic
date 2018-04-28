package com.example.huongthutran.sunmusic.ui.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.R;

public class FragmentPlaylistHome extends Fragment{
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_playlist_home, null);
        //Toast.makeText(getActivity(),"Gekk",Toast.LENGTH_LONG).show();
        return rootView;
    }
}
