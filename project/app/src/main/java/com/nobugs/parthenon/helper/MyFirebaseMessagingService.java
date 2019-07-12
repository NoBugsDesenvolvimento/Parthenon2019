package com.nobugs.parthenon.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nobugs.parthenon.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){


            String titulo = remoteMessage.getNotification().getTitle();
            String mensagem = remoteMessage.getNotification().getBody();

            exibirNotificacao(titulo, mensagem);
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

    }

    public void exibirNotificacao (String titulo, String mensagem){
        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String canal = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal).setContentTitle(titulo).setContentText(mensagem).setSmallIcon(R.drawable.ic_account_24dp).setSound(som);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(canal, "CANAL", NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(notificationChannel);

        }


        notificationManager.notify(0, notificacao.build());

    }
}
