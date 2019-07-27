package com.nobugs.parthenon.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.fragment.organizador.EnviarNoticias;
import com.nobugs.parthenon.fragment.organizador.EventoEditar;
import com.nobugs.parthenon.fragment.congressista.Duvidas;
import com.nobugs.parthenon.fragment.organizador.GerenciarAtividades;

public class NavigationScreenAdmin extends FragmentActivity {

    private static final int NUM_PAGES = 4;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private BottomNavigationView navView;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_screen_adm);

        viewPager = findViewById(R.id.screenPagerAdm);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabs = findViewById(R.id.tabsAdm);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(changeTabListener);

        navView = findViewById(R.id.nav_view_adm);
        navView.setOnNavigationItemSelectedListener(changeNavListener);
    }


    private TabLayout.OnTabSelectedListener changeTabListener = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch(tabs.getSelectedTabPosition()){
                case 0:
                    navView.setSelectedItemId(R.id.nav_prog_adm);
                    break;
                case 1:
                    navView.setSelectedItemId(R.id.nav_faq_adm);
                    break;
                case 2:
                    navView.setSelectedItemId(R.id.nav_noticias_adm);
                    break;
                case 3:
                    navView.setSelectedItemId(R.id.nav_evento_adm);
                    break;
            }
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener changeNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_prog_adm:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_faq_adm:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_noticias_adm:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.nav_evento_adm:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };


    //Acho isso aqui meio bobo
    /*@Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }*/

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new GerenciarAtividades();
                case 1:
                    return new Duvidas();
                case 2:
                    return new EnviarNoticias();
                case 3:
                    return new EventoEditar();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ""+position;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}