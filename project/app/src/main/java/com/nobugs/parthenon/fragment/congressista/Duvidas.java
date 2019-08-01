package com.nobugs.parthenon.fragment.congressista;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.activity.Duvida;
import com.nobugs.parthenon.activity.MapsActivity;
import com.nobugs.parthenon.activity.SubmitDuvidasActivity;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Perguntas.Pergunta;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Duvidas extends Fragment {
    private static RealmResults<Pergunta> perguntas;
    private FloatingActionButton floatingActionButtonDuvidas;
    private FloatingActionButton floatingActionButtonMaps;
    private AlertDialog alerta;
    final private ArrayList<View> pergs = new ArrayList<>();
    private static EditText searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_duvidas, container, false);

        floatingActionButtonDuvidas = rootView.findViewById(R.id.floatingActionButton);
        floatingActionButtonMaps = rootView.findViewById(R.id.floatingActionButtonMaps);


        floatingActionButtonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        });

        floatingActionButtonDuvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SubmitDuvidasActivity.class);
                startActivity(i); }});

        searchBar = rootView.findViewById(R.id.search_question);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Realm realm = RealmHelper.getRealm(getContext());
        perguntas = realm.where(Pergunta.class).findAll();

        LinearLayout scroll = getView().findViewById(R.id.perguntas);
        scroll.removeAllViews();

        int count = perguntas.size();
        Log.v("rgk", count+"");
        for (int i = count-1; i >= 0; i--) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null) {
                if(user.getPhotoUrl()!= null){
                    if (!user.getPhotoUrl().toString().equals("admin")) {

                    if (perguntas.get(i).getRespondida().equals("1")) {
                        LinearLayout templatePerg = (LinearLayout) getLayoutInflater().inflate(R.layout.template_perg, scroll, false);

                        Log.v("rgk", perguntas.get(i).getTitulo());

                        ((TextView) templatePerg.findViewById(R.id.pergunta)).setText(perguntas.get(i).getTitulo());

                        if (!perguntas.get(i).getRespondida().equals("0")) {
                            ((ImageView) templatePerg.findViewById(R.id.answered)).setImageResource(R.drawable.ic_answered);
                            final String key = perguntas.get(i).getKey();
                            templatePerg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent it = new Intent(getContext(), Duvida.class);
                                    it.putExtra("key", key);
                                    startActivity(it); }    }); }
                        else { ((ImageView) templatePerg.findViewById(R.id.answered)).setImageResource(R.drawable.ic_time);    }

                        scroll.addView(templatePerg); }
                    }}

                else {

                    LinearLayout templatePerg = (LinearLayout) getLayoutInflater().inflate(R.layout.template_perg, scroll, false);

                    Log.v("rgk", perguntas.get(i).getTitulo());

                    ((TextView) templatePerg.findViewById(R.id.pergunta)).setText(perguntas.get(i).getTitulo());

                    if (!perguntas.get(i).getRespondida().equals("0")) {
                        ((ImageView) templatePerg.findViewById(R.id.answered)).setImageResource(R.drawable.ic_answered);
                        final String key = perguntas.get(i).getKey();
                        templatePerg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it = new Intent(getContext(), Duvida.class);
                                it.putExtra("key", key);
                                startActivity(it); }}); }
                    else { ((ImageView) templatePerg.findViewById(R.id.answered)).setImageResource(R.drawable.ic_time); }
                    scroll.addView(templatePerg);
                }
            }

        }

        getView().findViewsWithText(pergs,"Perguntas",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int i, int i1, int i2) {
                int total = pergs.size();
                for (int j = 0; j < total; j++){
                    String textString = ((TextView) pergs.get(j).findViewById(R.id.pergunta)).getText().toString().toLowerCase();
                    if (!textString.contains(newText)){
                        pergs.get(j).setVisibility(View.GONE);
                    }else{
                        pergs.get(j).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



}
