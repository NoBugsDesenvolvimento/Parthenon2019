package com.nobugs.parthenon;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
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

        //FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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

            ProgramacaoTemplate templateProg = new ProgramacaoTemplate();
            Bundle bd = new Bundle();
            bd.putString("name", "Vo da aula de Android D+");
            bd.putString("autor", "Róger");
            bd.putString("time", "04:20");
            bd.putString("local", "Aqui em casa bb");
            rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
            rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
            rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
            rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);

            return rootView;
        }

    }

}
