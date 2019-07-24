package com.nobugs.parthenon.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Atividades.Atividade;
import com.nobugs.parthenon.model.Atividades.AtividadesAux;

import io.realm.Realm;

public class CriarAtividade extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_criar_atividade, container, false);
    }


    public void confirmar(View v){
        String titulo = v.findViewById(R.id.etTitulo).toString();
        String sumario = v.findViewById(R.id.etSumario).toString();
        String palestrante = v.findViewById(R.id.etPalestrante).toString();
        String local = v.findViewById(R.id.etLocal).toString();
        int vagas = Integer.parseInt(v.findViewById(R.id.etVagas).toString());
        String data = v.findViewById(R.id.etData).toString();
        String hora_inicial = v.findViewById(R.id.etHoraI).toString();
        String hora_final = v.findViewById(R.id.etHoraF).toString();

        String latlng = "não sei pegar ainda";

        AtividadesAux atvAux = new AtividadesAux(titulo, sumario, 1, local, data, hora_inicial, hora_final, vagas, "", latlng);

        FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
        DatabaseReference myRef = db.getReference("atividades/");
        myRef.setValue(atvAux);

        String key = myRef.getKey();

        Atividade atv = new Atividade(atvAux, key);
        Realm realm = RealmHelper.getRealm(getContext());
        RealmHelper.startTransaction();
        realm.insertOrUpdate(atv);
        RealmHelper.endTransaction();
    }
}
