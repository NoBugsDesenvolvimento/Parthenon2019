package com.nobugs.parthenon.fragment.congressista;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.activity.MapsActivity;
import com.nobugs.parthenon.activity.SubmitDuvidasActivity;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Perguntas.Pergunta;

import io.realm.Realm;
import io.realm.RealmResults;

public class Duvidas extends Fragment {
    private static RealmResults<Pergunta> perguntas;
    private FloatingActionButton floatingActionButtonDuvidas;
    private FloatingActionButton floatingActionButtonMaps;
    private AlertDialog alerta;

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

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Realm realm = RealmHelper.getRealm(getContext());
        perguntas = realm.where(Pergunta.class).findAll();

        LinearLayout scroll = getView().findViewById(R.id.questions);
        scroll.removeAllViews();

        int count = perguntas.size();
        for (int i = 0; i < count; i++) {
            /* Cria o coiso */

            /*
            CardView templateProg = (CardView) getLayoutInflater().inflate(R.layout.template_prog, scroll, false);

            ((TextView) templateProg.findViewById(R.id.name)).setText(atividadesData.get(i).getTitulo());
            ((TextView) templateProg.findViewById(R.id.time)).setText(atividadesData.get(i).getHora_inicial());
            ((TextView) templateProg.findViewById(R.id.local)).setText(atividadesData.get(i).getLocal());
            ((TextView) templateProg.findViewById(R.id.autor)).setText("esqueci tbm");

            scroll.addView(templateProg);
            */
        }
    }



}
