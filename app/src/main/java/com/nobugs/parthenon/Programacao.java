package com.nobugs.parthenon;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


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

            Realm.init(getContext());
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();

            Realm realm = Realm.getInstance(config);
            RealmResults<Event> events = realm.where(Event.class).findAll();

            int count = events.size();
            for (int i = 0; i < count; i++) {
                CardView templateProg = (CardView) inflater.inflate(R.layout.prog_template, scroll, false);

                ((TextView) templateProg.findViewById(R.id.name)).setText(events.get(i).getName());
                ((TextView) templateProg.findViewById(R.id.time)).setText(events.get(i).getTime());
                ((TextView) templateProg.findViewById(R.id.local)).setText("Me esqueci");
                ((TextView) templateProg.findViewById(R.id.autor)).setText("esqueci tbm");

                scroll.addView(templateProg);
            }

            return rootView;
        }

    }

}
