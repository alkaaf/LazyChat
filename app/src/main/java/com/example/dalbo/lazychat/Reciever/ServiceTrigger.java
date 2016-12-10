package com.example.dalbo.lazychat.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dalbo.lazychat.Service.NotifService;

/**
 * Created by dalbo on 12/10/2016.
 */

public class ServiceTrigger extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentSvc = new Intent(context, NotifService.class);
        context.startService(intentSvc);
    }
}
