package com.example.huongthutran.sunmusic.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.ui.Fragment.Home.FragmentPlaylistHome;
import com.example.huongthutran.sunmusic.ui.Fragment.Home.FragmentSongsHome;

public class FragmentHome extends android.support.v4.app.Fragment {
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_allmusic, null);
        Toast.makeText(getActivity(),"Gekk",Toast.LENGTH_LONG).show();
        FragmentPlaylistHome fragmentPlaylistHome = new FragmentPlaylistHome();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_album, fragmentPlaylistHome);
        fragmentTransaction.commit();

        FragmentSongsHome fragmentSongsHome = new FragmentSongsHome();
        FragmentManager fragmentManagers = getFragmentManager();
        FragmentTransaction fragmentTransactions = fragmentManager.beginTransaction();
        fragmentTransactions.add(R.id.frame_song, fragmentSongsHome);
        fragmentTransactions.commit();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_album, new FragmentPlaylistHome(), String.valueOf(new FragmentPlaylistHome().getId())).commit();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_song, new FragmentSongsHome(), String.valueOf(new FragmentSongsHome().getId())).commit();

        return rootView;
    }
}
