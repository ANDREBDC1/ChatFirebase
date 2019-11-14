package com.example.chatfirebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMServico extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("Memsagem", remoteMessage.getMessageId());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.i("Token", s);
    }
}
