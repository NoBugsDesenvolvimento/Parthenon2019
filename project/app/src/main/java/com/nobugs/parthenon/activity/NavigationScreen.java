package com.nobugs.parthenon.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.fragment.Faq;
import com.nobugs.parthenon.fragment.Profile;
import com.nobugs.parthenon.fragment.Programacao;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.internal.IOException;

public class NavigationScreen extends FragmentActivity {

    private static final int NUM_PAGES = 4;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private BottomNavigationView navView;
    private TabLayout tabs;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_screen);

        viewPager = findViewById(R.id.screenPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(changeTabListener);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(changeNavListener);

        //Iniciar Realm
       /* Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        //Banco de Dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Atividades");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                switch (dataSnapshot.getRef().getKey()){
                    case "Atividades":
                        GenericTypeIndicator<List<Event>> t = new GenericTypeIndicator<List<Event>>() {};
                        List<Event> value = dataSnapshot.getValue(t);
                        for (int i = 0; i < value.size(); i++){
                            try{
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(value.get(i));
                                realm.commitTransaction();
                            } catch (IOException e){
                                Log.v("rgk", e.getMessage());
                            }
                        }

                        break;
                    case "qualquer outra merda":
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        RealmResults<Event> events = realm.where(Event.class).findAll(); */
    }


    private TabLayout.OnTabSelectedListener changeTabListener = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch(tabs.getSelectedTabPosition()){
                case 0:
                    navView.setSelectedItemId(R.id.nav_prog);
                    break;
                case 1:
                    navView.setSelectedItemId(R.id.nav_noticias);
                    break;
                case 2:
                    navView.setSelectedItemId(R.id.nav_faq);
                    break;
                case 3:
                    navView.setSelectedItemId(R.id.nav_perfil);
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
                case R.id.nav_prog:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_noticias:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_faq:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.nav_perfil:
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
                    return new Programacao();
                case 1:
                    return new Faq();
                case 2:
                    return new Profile();
                case 3:
                    return new Profile();
            }
            return new Faq();
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