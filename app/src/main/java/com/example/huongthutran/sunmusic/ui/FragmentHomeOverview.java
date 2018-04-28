package com.example.huongthutran.sunmusic.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.example.huongthutran.sunmusic.R;
import com.example.huongthutran.sunmusic.ui.Fragment.FragmentHome;

public class FragmentHomeOverview  extends Fragment implements View.OnTouchListener{
    private View rootView;
    ViewPager  view_pager_home;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(getActivity(),"Hrllll",Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.view_pager_home, new FragmentHome(), String.valueOf(new FragmentHome().getId())).commit();
                    return true;
                case R.id.navigation_dashboard:
                   // replaceFragment(FragmentHome());
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_overview, null);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.view_pager_home, new FragmentHome(), String.valueOf(new FragmentHome().getId())).commit();

        return rootView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
    private void replaceFragment(android.app.Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_home, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
};

