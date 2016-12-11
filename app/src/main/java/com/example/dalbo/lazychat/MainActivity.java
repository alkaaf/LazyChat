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
import com.example.dalbo.lazychat.Dialog.CreateRoom;
import com.example.dalbo.lazychat.Model.ChatRoomModel;
import com.example.dalbo.lazychat.Service.NotifService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity {
    FirebaseDatabase fbdb;
    DatabaseReference dbRef;
    ChatroomListAdapter adapter;
    RecyclerView recChatRoomList;
    Dialog dNewRoom;
    public static int started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NotifService.started == 0) {
            Intent intent = new Intent(this, NotifService.class);
            startService(intent);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        started = 1;
        setSupportActionBar(toolbar);
        fbdb  = FirebaseDatabase.getInstance();
//        fbdb.setPersistenceEnabled(true);
        dbRef = fbdb.getReference().child(Config.DBNAME);
        adapter = new ChatroomListAdapter(this);
        recChatRoomList = (RecyclerView) findViewById(R.id.chatroomlist);
        recChatRoomList.setLayoutManager(new LinearLayoutManager(this));
        recChatRoomList.setAdapter(adapter);
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                long expdate = Long.parseLong(dataSnapshot.child("expdate").getValue().toString());
                if (expdate > System.currentTimeMillis()) {
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

    @Override
    protected void onStart() {
        super.onStart();
        started = 1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        started = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(c, SettingActivity.class));
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
