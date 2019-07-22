package com.nobugs.parthenon.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {

    private static Realm realm;

    public static Realm getRealm(Context ctx){
        if (realm == null){
            //Iniciar Realm
            Realm.init(ctx);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }
        return realm;
    }

    public static void dropDatabase(){
        if (realm != null) {
            startTransaction();
            realm.deleteAll();
            endTransaction();
        }
    }

    public static void startTransaction(){

        if (realm != null) {
            realm.beginTransaction();
        }
    }
    public static void endTransaction(){
        if (realm != null) {
            realm.commitTransaction();
        }
    }
}
