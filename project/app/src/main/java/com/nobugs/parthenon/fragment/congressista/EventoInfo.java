package com.nobugs.parthenon.fragment.congressista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.activity.LoginActivity;
import com.nobugs.parthenon.activity.MapsActivity;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Evento;
import com.nobugs.parthenon.model.Logado;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventoInfo extends Fragment {
    private FirebaseAuth auth;
    private Button logout, mapsButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_evento, container, false);

        mapsButton = rootView.findViewById(R.id.buttonMaps);
        logout = rootView.findViewById(R.id.buttonLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{auth.signOut();

                    Realm realm = RealmHelper.getRealm(getContext());

                    Logado user = new Logado("offline");
                    RealmHelper.startTransaction();
                    realm.insertOrUpdate(user);
                    RealmHelper.endTransaction();

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent); }
                catch (Exception e){
                    e.printStackTrace(); }
            }
        });
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);

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
        ((TextView) getView().findViewById(R.id.tvOrg)).setText(evt.get(0).getOrganizacao());
        ((TextView) getView().findViewById(R.id.tvDia)).setText(evt.get(0).getData());
        ((TextView) getView().findViewById(R.id.tvLocal)).setText(evt.get(0).getLocal());
        ((TextView) getView().findViewById(R.id.tvInfo)).setText(evt.get(0).getInfo());

    }



            }
