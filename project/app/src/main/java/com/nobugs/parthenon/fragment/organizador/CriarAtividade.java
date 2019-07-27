package com.nobugs.parthenon.fragment.organizador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
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
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_criar_atividade, container, false);

        rootView.findViewById(R.id.btCriar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar(rootView);
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void confirmar(View v){
        String titulo = ((EditText) v.findViewById(R.id.etTitulo)).getText().toString();
        String sumario = ((EditText) v.findViewById(R.id.etSumario)).getText().toString();
        String palestrante = ((EditText) v.findViewById(R.id.etPalestrante)).getText().toString();
        String local = ((EditText) v.findViewById(R.id.etLocal)).getText().toString();
        String str_vagas = ((EditText) v.findViewById(R.id.etVagas)).getText().toString();
        int vagas = 0;
        if (!str_vagas.equals(""))
            vagas = Integer.parseInt(str_vagas);
        String data = ((EditText) v.findViewById(R.id.etData)).getText().toString();
        String hora_inicial = ((EditText) v.findViewById(R.id.etHoraI)).getText().toString();
        String hora_final = ((EditText) v.findViewById(R.id.etHoraF)).getText().toString();

        String empty = "";
        if (titulo.equals(""))
            empty += "\n- Título";
        if (palestrante.equals(""))
            empty += "\n- Palestrante(s)";
        if (data.equals(""))
            empty += "\n- Data";
        if (hora_inicial.equals(""))
            empty += "\n- Hora (início)";

        if (!empty.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.title_erro_criacao)
                    .setMessage(empty);
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        String latlng = "não sei pegar ainda";

        AtividadesAux atvAux = new AtividadesAux(titulo, sumario, 1, local, data, hora_inicial, hora_final, vagas, "", latlng);

        FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
        DatabaseReference myRef = db.getReference("atividades/").push();
        myRef.setValue(atvAux);

        String key = myRef.getKey();

        Atividade atv = new Atividade(atvAux, key);
        Realm realm = RealmHelper.getRealm(getContext());
        RealmHelper.startTransaction();
        realm.insertOrUpdate(atv);
        RealmHelper.endTransaction();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.title_confirma_criacao)
                .setMessage("   ");
        AlertDialog dialog = builder.create();
        dialog.show();

        ((EditText) v.findViewById(R.id.etTitulo)).setText("");
        ((EditText) v.findViewById(R.id.etSumario)).setText("");
        ((EditText) v.findViewById(R.id.etPalestrante)).setText("");
        ((EditText) v.findViewById(R.id.etLocal)).setText("");
        ((EditText) v.findViewById(R.id.etVagas)).setText("");
        ((EditText) v.findViewById(R.id.etData)).setText("");
        ((EditText) v.findViewById(R.id.etHoraI)).setText("");
        ((EditText) v.findViewById(R.id.etHoraF)).setText("");

    }
}
