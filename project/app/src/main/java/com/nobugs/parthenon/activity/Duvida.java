package com.nobugs.parthenon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Perguntas.Pergunta;

import io.realm.Realm;
import io.realm.RealmResults;

public class Duvida extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Já ta pegando a key da pergunta pra povoar a tela
        Intent it = getIntent();
        String key = it.getStringExtra("key");
        Log.v("rgk", key);

        Realm realm = RealmHelper.getRealm(this);
        RealmResults<Pergunta> pergunta = realm.where(Pergunta.class).equalTo("key", key).findAll();
        // pergunta.get(0) já é a pergunta
    }
}
