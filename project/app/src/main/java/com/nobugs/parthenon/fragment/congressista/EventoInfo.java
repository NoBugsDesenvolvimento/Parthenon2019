package com.nobugs.parthenon.fragment.congressista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Evento;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventoInfo extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_evento, container, false);

    }

    @Override
    public void onResume() {
        super.onResume();

        Realm realm = RealmHelper.getRealm(getContext());
        RealmResults<Evento> evt = realm.where(Evento.class).findAll();

        Log.v("rgk", evt.size()+"");

        ((TextView) getView().findViewById(R.id.evt_header)).setText(evt.get(0).getNome());
        ((TextView) getView().findViewById(R.id.tvOrg)).setText(evt.get(0).getOrganizacao());
        ((TextView) getView().findViewById(R.id.tvDia)).setText(evt.get(0).getData());
        ((TextView) getView().findViewById(R.id.tvLocal)).setText(evt.get(0).getLocal());
        ((TextView) getView().findViewById(R.id.tvInfo)).setText(evt.get(0).getInfo());

    }
}
