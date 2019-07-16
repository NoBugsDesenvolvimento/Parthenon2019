package com.nobugs.parthenon.helper;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.nobugs.parthenon.model.Atividades.Atividade;

import java.util.List;

import io.realm.Realm;
import io.realm.internal.IOException;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static FirebaseDatabase referenciaDatabase;

    public static FirebaseDatabase getDatabase(){
        if ( referenciaDatabase == null){
            referenciaDatabase = FirebaseDatabase.getInstance();
        }
        return referenciaDatabase;
    }

    //retorna a referencia do database
    public static DatabaseReference getFirebase(){
        if( referenciaFirebase == null ){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if( referenciaAutenticacao == null ){
            referenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return referenciaAutenticacao;
    }

    public static void updateValues(String reference, Context ctx){
        final Realm realm = RealmHelper.getRealm(ctx);
        RealmHelper.dropDatabase();
        FirebaseDatabase db = getDatabase();
        DatabaseReference myRef = db.getReference(reference);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                switch (dataSnapshot.getRef().getKey()){
                    case "atividades":
                        RealmHelper.startTransaction();
                        GenericTypeIndicator<List<Atividade>> t = new GenericTypeIndicator<List<Atividade>>() {};
                        List<Atividade> value = dataSnapshot.getValue(t);
                        for (int i = 0; i < value.size(); i++){
                            try{
                                realm.copyToRealmOrUpdate(value.get(i));
                            } catch (IOException e){
                                Log.v("rgk", e.getMessage());
                            }
                        }
                        RealmHelper.endTransaction();
                        break;
                    case "qualquer outra merda":
                        break;

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

}
