package com.nobugs.parthenon.fragment.organizador;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Atividades.Atividade;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class GerenciarAtividades extends Fragment {
    private static RealmResults<Atividade> atividades;
    private static EditText searchBar;
    private static String searchText = "";
    final private ArrayList<View> atvs = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_gerenciar_atividades, container, false);

        rootView.findViewById(R.id.btAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrEdit("");
            }
        });

        searchBar = rootView.findViewById(R.id.search_atv_adm);

        return rootView;
    }

        @Override
        public void onResume() {
            super.onResume();

            Realm realm = RealmHelper.getRealm(getContext());
            atividades = realm.where(Atividade.class).sort("hora_inicial").findAll();

            LinearLayout scroll = getView().findViewById(R.id.atividades_adm);
            scroll.removeAllViews();

            int count = atividades.size();
            for (int i = 0; i < count; i++) {
                CardView templateProg = (CardView) getLayoutInflater().inflate(R.layout.template_prog, scroll, false);
                final String key = atividades.get(i).getKey();
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

                ((TextView) templateProg.findViewById(R.id.name)).setText(atividades.get(i).getTitulo());
                ((TextView) templateProg.findViewById(R.id.time)).setText(atividades.get(i).getHora_inicial());
                ((TextView) templateProg.findViewById(R.id.local)).setText(atividades.get(i).getLocal());
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
            searchBar.setText(searchText);
            searchBar.setSelection(searchBar.getText().length());
        }


    public void createOrEdit(String key){
        Intent it = new Intent(getContext(), EditarAtividade.class).putExtra("key", key);
        startActivity(it);
    }
}
