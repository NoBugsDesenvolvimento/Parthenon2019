package com.nobugs.parthenon.fragment.congressista;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.activity.MapsActivity;
import com.nobugs.parthenon.activity.SubmitDuvidasActivity;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Atividades.Atividade;
import com.nobugs.parthenon.model.Atividades.AtividadesAux;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class Programacao extends Fragment {
    private int num_pages;
    private DateSlidePagerAdapter dateCollection;
    private ViewPager viewPager;
    private Set<Date> datesAux;
    private FloatingActionButton floatingActionButton;
    private static Vector<String> dates;
    private static RealmResults<Atividade> atividades;
    private static EditText searchBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_programacao, container, false);

        /*floatingActionButton = rootView.findViewById(R.id.floatingActionButtonProgramacao);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        }); */

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

        searchBar = rootView.findViewById(R.id.search_atv_user);

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
        final private ArrayList<View> atvs = new ArrayList<>();

        @Override
        public void onResume() {
            super.onResume();

            LinearLayout scroll = getView().findViewById(R.id.date);
            scroll.removeAllViews();

            Bundle args = getArguments();
            RealmResults<Atividade> atividadesData = atividades.where().contains("data", dates.get(args.getInt("tab"))).findAll();
            int count = atividadesData.size();
            for (int i = 0; i < count; i++) {
                CardView templateProg = (CardView) getLayoutInflater().inflate(R.layout.template_prog, scroll, false);

                ((TextView) templateProg.findViewById(R.id.name)).setText(atividadesData.get(i).getTitulo());
                ((TextView) templateProg.findViewById(R.id.time)).setText(atividadesData.get(i).getHora_inicial());
                ((TextView) templateProg.findViewById(R.id.local)).setText(atividadesData.get(i).getLocal());
                ((TextView) templateProg.findViewById(R.id.autor)).setText("esqueci tbm");

                scroll.addView(templateProg);
            }

            getView().findViewsWithText(atvs,"Atividades",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int i, int i1, int i2) {
                    int total = atvs.size();
                    for (int j = 0; j < total; j++){
                        String textString = ((TextView) atvs.get(j).findViewById(R.id.name)).getText().toString().toLowerCase();
                        if (!textString.contains(newText)){
                            atvs.get(j).setVisibility(View.GONE);
                        }else{
                            atvs.get(j).setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.days, container, false);

            return rootView;
        }

    }

}
