package com.nobugs.parthenon.fragment.organizador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Atividades.Atividade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmResults;

public class GerenciarAtividades extends Fragment {
    private int num_pages;
    private DateSlidePagerAdapter dateCollection;
    private ViewPager viewPager;
    private Set<Date> datesAux;
    private static Vector<String> dates;
    private static RealmResults<Atividade> atividades;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_gerenciar_atividades, container, false);

        datesAux = new TreeSet<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Realm realm = RealmHelper.getRealm(getContext());

        atividades = realm.where(Atividade.class).findAll();
        for (Atividade atv : atividades){
            try {
                datesAux.add(formatter.parse(atv.getData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        dates = new Vector<>();
        for (Date date : datesAux) dates.add(formatter.format(date).substring(0, 5));
        num_pages = dates.size();
        Log.v("rgk", num_pages+"");

        rootView.findViewById(R.id.btAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrEdit("");
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dateCollection = new DateSlidePagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.date_pager_adm);
        viewPager.setAdapter(dateCollection);
        TabLayout tabs = view.findViewById(R.id.days_tabs_adm);
        tabs.setupWithViewPager(viewPager);
    }

    private class DateSlidePagerAdapter extends FragmentStatePagerAdapter {
        private DateSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new DayFragment();
            Bundle bd = new Bundle();
            bd.putInt("tab", position);
            fragment.setArguments(bd);
            return fragment;
        }

        @Override
        public int getCount() {
            return num_pages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dates.get(position);
        }

    }

    public static class DayFragment extends Fragment {

        @Override
        public void onResume() {
            super.onResume();

            Realm realm = RealmHelper.getRealm(getContext());
            atividades = realm.where(Atividade.class).findAll();

            LinearLayout scroll = getView().findViewById(R.id.date);
            scroll.removeAllViews();

            Bundle args = getArguments();
            RealmResults<Atividade> atividadesData = atividades.where().contains("data", dates.get(args.getInt("tab"))).findAll();
            int count = atividadesData.size();
            for (int i = 0; i < count; i++) {
                CardView templateProg = (CardView) getLayoutInflater().inflate(R.layout.template_prog, scroll, false);
                final String key = atividadesData.get(i).getKey();
                templateProg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(getContext(), EditarAtividade.class);
                        if (!key.equals("")){
                            it.putExtra("key", key);
                        }
                        startActivity(it);
                    }
                });

                ((TextView) templateProg.findViewById(R.id.name)).setText(atividadesData.get(i).getTitulo());
                ((TextView) templateProg.findViewById(R.id.time)).setText(atividadesData.get(i).getHora_inicial());
                ((TextView) templateProg.findViewById(R.id.local)).setText(atividadesData.get(i).getLocal());
                ((TextView) templateProg.findViewById(R.id.autor)).setText("esqueci tbm");

                scroll.addView(templateProg);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.days, container, false);

            return rootView;
        }

    }

    public void createOrEdit(String key){
        Intent it = new Intent(getContext(), EditarAtividade.class);
        startActivity(it);
    }
}
