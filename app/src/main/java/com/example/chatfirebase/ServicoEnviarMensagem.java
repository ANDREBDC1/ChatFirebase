package com.example.chatfirebase;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicoEnviarMensagem extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Teste", intent.getStringExtra("id"));
        return super.onStartCommand(intent, flags, startId);
    }
}
