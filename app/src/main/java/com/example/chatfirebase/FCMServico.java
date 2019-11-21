package com.example.chatfirebase;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMServico extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, ServicoEnviarMensagem.class);
        intent.putExtra("id", remoteMessage.getMessageId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else{
            startService(intent);
        }

        super.onMessageReceived(remoteMessage);
    }



    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.i("Token", s);
    }
}
