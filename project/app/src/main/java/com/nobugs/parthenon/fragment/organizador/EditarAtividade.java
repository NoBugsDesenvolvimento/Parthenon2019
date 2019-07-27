package com.nobugs.parthenon.fragment.organizador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Atividades.Atividade;
import com.nobugs.parthenon.model.Atividades.AtividadesAux;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditarAtividade extends FragmentActivity {

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_atividade);

        Intent it = getIntent();
        key = it.getStringExtra("key");
        if (!key.equals("")){
            Realm realm = RealmHelper.getRealm(this);
            RealmResults<Atividade> atividade = realm.where(Atividade.class).equalTo("key", key).findAll();
            Log.v("rgk", atividade.size()+"");

            ((EditText)findViewById(R.id.etTitulo)).setText(atividade.get(0).getTitulo());
            ((EditText)findViewById(R.id.etSumario)).setText(atividade.get(0).getSumario());
            ((EditText)findViewById(R.id.etPalestrante)).setText("esqueci de colocar o palestrante lul");
            ((EditText)findViewById(R.id.etLocal)).setText(atividade.get(0).getLocal());
            ((EditText)findViewById(R.id.etVagas)).setText(atividade.get(0).getVagas()+"");
            ((EditText)findViewById(R.id.etData)).setText(atividade.get(0).getData());
            ((EditText)findViewById(R.id.etHoraI)).setText(atividade.get(0).getHora_inicial());
            ((EditText)findViewById(R.id.etHoraF)).setText(atividade.get(0).getHora_final());
        }
    }


    public void confirmar(View v){
        String titulo = ((EditText) findViewById(R.id.etTitulo)).getText().toString();
        String sumario = ((EditText) findViewById(R.id.etSumario)).getText().toString();
        String palestrante = ((EditText) findViewById(R.id.etPalestrante)).getText().toString();
        String local = ((EditText) findViewById(R.id.etLocal)).getText().toString();
        String str_vagas = ((EditText) findViewById(R.id.etVagas)).getText().toString();
        int vagas = 0;
        if (!str_vagas.equals(""))
            vagas = Integer.parseInt(str_vagas);
        String data = ((EditText) findViewById(R.id.etData)).getText().toString();
        String hora_inicial = ((EditText) findViewById(R.id.etHoraI)).getText().toString();
        String hora_final = ((EditText) findViewById(R.id.etHoraF)).getText().toString();

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_erro_criacao)
                    .setMessage(empty);
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        String latlng = "não sei pegar ainda";

        final AtividadesAux atvAux = new AtividadesAux(titulo, sumario, 1, local, data, hora_inicial, hora_final, vagas, "", latlng);

        if (key == null) {
            FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
            DatabaseReference myRef = db.getReference("atividades/").push();
            myRef.setValue(atvAux);

            String k = myRef.getKey();

            Atividade atv = new Atividade(atvAux, k);
            Realm realm = RealmHelper.getRealm(this);
            RealmHelper.startTransaction();
            realm.insertOrUpdate(atv);
            RealmHelper.endTransaction();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_confirma_criacao)
                    .setMessage("   ");
            AlertDialog dialog = builder.create();
            dialog.show();

            ((EditText) findViewById(R.id.etTitulo)).setText("");
            ((EditText) findViewById(R.id.etSumario)).setText("");
            ((EditText) findViewById(R.id.etPalestrante)).setText("");
            ((EditText) findViewById(R.id.etLocal)).setText("");
            ((EditText) findViewById(R.id.etVagas)).setText("");
            ((EditText) findViewById(R.id.etData)).setText("");
            ((EditText) findViewById(R.id.etHoraI)).setText("");
            ((EditText) findViewById(R.id.etHoraF)).setText("");
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_confirma_edicao)
                    .setMessage(R.string.msg_confirma_edicao);
            builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
                    DatabaseReference myRef = db.getReference("atividades/" + key);
                    myRef.setValue(atvAux);

                    Atividade atv = new Atividade(atvAux, key);
                    Realm realm = RealmHelper.getRealm(getBaseContext());
                    RealmHelper.startTransaction();
                    realm.insertOrUpdate(atv);
                    RealmHelper.endTransaction();

                    Toast toast = Toast.makeText(getBaseContext(), R.string.editado_sucesso,Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
