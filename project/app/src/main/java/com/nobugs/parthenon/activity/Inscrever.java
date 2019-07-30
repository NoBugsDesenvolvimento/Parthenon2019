package com.nobugs.parthenon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Atividades.Atividade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmResults;

public class Inscrever extends AppCompatActivity {

    private int num_pages;
    private DateSlidePagerAdapter dateCollection;
    private ViewPager viewPager;
    private Set<Date> datesAux;
    private static Vector<String> dates;
    private static RealmResults<Atividade> atividades;
    private static long inscCount = 0;
    private static String userKey = "fogogo";
    private static EditText searchBar;
    private static String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrever);

        final FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
        final DatabaseReference myRef = db.getReference("inscricoes/" + userKey);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inscCount = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Realm realm = RealmHelper.getRealm(this);

        atividades = realm.where(Atividade.class).findAll();
        datesAux = new TreeSet<>();
        int count = atividades.size();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < count; i++) {
            try {
                datesAux.add(formatter.parse(atividades.get(i).getData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        dates = new Vector<>();
        for (Date date : datesAux) dates.add(formatter.format(date).substring(0, 5));
        num_pages = dates.size();

        dateCollection = new DateSlidePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.datePager);
        viewPager.setAdapter(dateCollection);
        TabLayout tabs = findViewById(R.id.daysTabs);
        tabs.setupWithViewPager(viewPager);

        searchBar = findViewById(R.id.search_insc);

    }

    private class DateSlidePagerAdapter extends FragmentStatePagerAdapter {
        private DateSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            searchText = searchBar.getText().toString();
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

        final private ArrayList<View> insc = new ArrayList<>();

        @Override
        public void onResume() {
            super.onResume();
            LinearLayout scroll = getView().findViewById(R.id.date);
            scroll.removeAllViews();

            Bundle args = getArguments();
            final RealmResults<Atividade> atividadesData = atividades.where().contains("data", dates.get(args.getInt("tab"))).findAll();
            int count = atividadesData.size();
            for (int i = 0; i < count; i++) {
                CardView templateInsc = (CardView) getLayoutInflater().inflate(R.layout.template_insc, scroll, false);

                ((TextView) templateInsc.findViewById(R.id.name)).setText(atividadesData.get(i).getTitulo());
                ((TextView) templateInsc.findViewById(R.id.time)).setText(atividadesData.get(i).getHora_inicial());
                ((TextView) templateInsc.findViewById(R.id.local)).setText(atividadesData.get(i).getLocal());
                ((TextView) templateInsc.findViewById(R.id.autor)).setText("esqueci tbm");

                final CheckBox insc = templateInsc.findViewById(R.id.inscrever);
                final FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
                final String atividadeKey = atividadesData.get(i).getKey();
                final DatabaseReference myRef = db.getReference("inscricoes/" + userKey + "/" + atividadeKey);

                insc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            if (inscCount < 5) {
                                inscCount++;
                                // 0 para indicar inscrito e 1 para confirmado
                                myRef.setValue(0);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage(R.string.limite_insc_text)
                                        .setTitle(R.string.limite_insc_title);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                insc.setChecked(false);
                            }
                        }else{
                            inscCount--;
                            myRef.removeValue();
                        }
                    }
                });

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            insc.setOnCheckedChangeListener (null);
                            insc.setChecked(true);
                            insc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (b){
                                        if (inscCount < 5) {
                                            inscCount++;
                                            // 0 para indicar inscrito e 1 para confirmado
                                            myRef.setValue(0);
                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setMessage(R.string.limite_insc_text)
                                                    .setTitle(R.string.limite_insc_title);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                            insc.setChecked(false);
                                        }
                                    }else{
                                        inscCount--;
                                        myRef.removeValue();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                scroll.addView(templateInsc);
            }

            getView().findViewsWithText(insc,"Inscricoes",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int i, int i1, int i2) {
                    int total = insc.size();
                    for (int j = 0; j < total; j++){
                        String textString = ((TextView) insc.get(j).findViewById(R.id.name)).getText().toString().toLowerCase();
                        if (!textString.contains(newText)){
                            insc.get(j).setVisibility(View.GONE);
                        }else{
                            insc.get(j).setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            searchBar.setText(searchText);
            searchBar.setSelection(searchBar.getText().length());
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.days, container, false);

            return rootView;
        }

    }
}
