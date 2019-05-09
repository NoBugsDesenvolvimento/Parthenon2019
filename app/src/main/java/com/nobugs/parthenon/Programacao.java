package com.nobugs.parthenon;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class Programacao extends Fragment {
    private static final int NUM_PAGES = 4;
    DateSlidePagerAdapter dateCollection;
    private ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.programacao, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dateCollection = new DateSlidePagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.datePager);
        viewPager.setAdapter(dateCollection);
        TabLayout tabs = view.findViewById(R.id.daysTabs);
        tabs.setupWithViewPager(viewPager);
    }

    private class DateSlidePagerAdapter extends FragmentStatePagerAdapter {
        private DateSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new DayFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "0"+(position+1)+"/05";
        }

    }

    public static class DayFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.days, container, false);

            LinearLayout scroll = rootView.findViewById(R.id.date);


            for (int i = 0; i < 10; i++) {
                CardView templateProg = (CardView) inflater.inflate(R.layout.prog_template, scroll, false);

                Bundle bd = new Bundle();
                bd.putString("name", "Vo da aula de Android D+");
                bd.putString("autor", "Róger");
                bd.putString("time", "04:20");
                bd.putString("local", "Aqui em casa bb");

                ((TextView) templateProg.findViewById(R.id.name)).setText(bd.getString("name"));
                ((TextView) templateProg.findViewById(R.id.time)).setText(bd.getString("time"));
                ((TextView) templateProg.findViewById(R.id.local)).setText(bd.getString("local"));
                ((TextView) templateProg.findViewById(R.id.autor)).setText(bd.getString("autor"));

                scroll.addView(templateProg);
            }

            return rootView;
        }

    }

}
