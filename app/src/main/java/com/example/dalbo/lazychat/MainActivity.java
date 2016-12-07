package com.example.dalbo.lazychat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dalbo.lazychat.Adapter.ChatroomListAdapter;
import com.example.dalbo.lazychat.Adapter.FirebaseChatroomAdapter;
import com.example.dalbo.lazychat.Dialog.CreateRoom;
import com.example.dalbo.lazychat.Model.ChatRoomModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity {
    DatabaseReference dbRef;
    ChatroomListAdapter adapter;
    FirebaseChatroomAdapter FBAdapter;
    RecyclerView recChatRoomList;
    Dialog dNewRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbRef = FirebaseDatabase.getInstance().getReference().child(Config.DBNAME);
        adapter = new ChatroomListAdapter(this);
        recChatRoomList = (RecyclerView) findViewById(R.id.chatroomlist);
        recChatRoomList.setLayoutManager(new LinearLayoutManager(this));
        recChatRoomList.setAdapter(adapter);
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                long expdate = Long.parseLong(dataSnapshot.child("expdate").getValue().toString());
                if(expdate > System.currentTimeMillis()) {
                    String name = dataSnapshot.child("roomname").getValue().toString();
                    String desc = dataSnapshot.child("roomdesc").getValue().toString();
                    String expired = dataSnapshot.child("expired").getValue().toString();
                    String key = dataSnapshot.getKey();
                    ChatRoomModel crm = new ChatRoomModel(name, desc, expdate, expired);
                    crm.setKey(key);
                    adapter.addNewData(crm);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dNewRoom = new CreateRoom(MainActivity.this);
                dNewRoom.setCancelable(true);
                dNewRoom.show();
            }
        });
    }



}
