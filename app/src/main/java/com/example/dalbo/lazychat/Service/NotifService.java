package com.example.dalbo.lazychat.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dalbo.lazychat.ChatActivity;
import com.example.dalbo.lazychat.MainActivity;
import com.example.dalbo.lazychat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.R.attr.key;

/**
 * Created by dalbo on 12/10/2016.
 */

public class NotifService extends Service {
    public static int started = 0;
    DatabaseReference dbRef;
    NotificationManager manager;
    Notification notif;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        started = 1;
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DataSnapshot ds = dataSnapshot;
                if (MainActivity.started == 0 && ChatActivity.started == 0) {
                    Intent chatIntent = new Intent(NotifService.this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(NotifService.this,0,chatIntent,0);
                    notif = new Notification.Builder(NotifService.this)
                            .setContentTitle("New message")
                            .setContentText("Click to open app!")
                            .setSmallIcon(R.drawable.lazy_logo)
                            .setContentIntent(pendingIntent)
                            .setVibrate(new long[]{1000,1000})
                            .build();
                    notif.flags = Notification.FLAG_AUTO_CANCEL;
                    manager.notify(1, notif);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        started = 0;
    }
}
