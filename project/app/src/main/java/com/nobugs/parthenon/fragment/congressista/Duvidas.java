package com.nobugs.parthenon.fragment.congressista;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.activity.Duvida;
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

        LinearLayout scroll = getView().findViewById(R.id.perguntas);
        scroll.removeAllViews();

        int count = perguntas.size();
        Log.v("rgk", count+"");
        for (int i = 0; i < count; i++) {
            LinearLayout templatePerg = (LinearLayout) getLayoutInflater().inflate(R.layout.template_perg, scroll, false);

            Log.v("rgk", perguntas.get(i).getTitulo());

            ((TextView) templatePerg.findViewById(R.id.pergunta)).setText(perguntas.get(i).getTitulo());
            if (!perguntas.get(i).getRespondida().equals("0")){
                ((ImageView) templatePerg.findViewById(R.id.answered)).setImageResource(R.drawable.ic_answered);
            }else{
                ((ImageView) templatePerg.findViewById(R.id.answered)).setImageResource(R.drawable.ic_time);
            }

            final String key = perguntas.get(i).getKey();
            templatePerg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(getContext(), Duvida.class);
                    it.putExtra("key", key);
                    startActivity(it);
                }
            });
            Log.v("rgk", "alguem me ajuda" +i);
            scroll.addView(templatePerg);
        }
    }



}
