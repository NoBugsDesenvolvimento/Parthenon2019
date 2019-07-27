package com.nobugs.parthenon.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Evento;
import com.nobugs.parthenon.model.EventoAux;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventoEditar extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_evento_editar, container, false);

        FloatingActionButton save = (FloatingActionButton) rootView.findViewById(R.id.btSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarEvento();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Realm realm = RealmHelper.getRealm(getContext());
        RealmResults<Evento> evt = realm.where(Evento.class).findAll();

        Log.v("rgk", evt.size()+"");

        ((TextView) getView().findViewById(R.id.evt_header)).setText(evt.get(0).getNome());
        ((TextView) getView().findViewById(R.id.edtOrg)).setText(evt.get(0).getOrganizacao());
        ((TextView) getView().findViewById(R.id.edtDia)).setText(evt.get(0).getData());
        ((TextView) getView().findViewById(R.id.edtLocal)).setText(evt.get(0).getLocal());
        ((TextView) getView().findViewById(R.id.edtInfo)).setText(evt.get(0).getInfo());
    }

    private void editarEvento(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.editar_evento_title)
                .setMessage(R.string.editar_evento_msg);
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nome = ((TextView) getView().findViewById(R.id.evt_header)).getText().toString();
                String org = ((TextView) getView().findViewById(R.id.edtOrg)).getText().toString();
                String dia = ((TextView) getView().findViewById(R.id.edtDia)).getText().toString();
                String local = ((TextView) getView().findViewById(R.id.edtLocal)).getText().toString();
                String info = ((TextView) getView().findViewById(R.id.edtInfo)).getText().toString();

                EventoAux evtAux = new EventoAux(nome, org, local, dia, info);

                FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
                DatabaseReference myRef = db.getReference("evento");
                myRef.setValue(evtAux);

                Evento evt = new Evento(evtAux, "evento");
                Realm realm = RealmHelper.getRealm(getContext());
                RealmHelper.startTransaction();
                realm.insertOrUpdate(evt);
                RealmHelper.endTransaction();
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
