package com.example.dalbo.lazychat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dalbo.lazychat.Adapter.ChatListAdapter;
import com.example.dalbo.lazychat.Model.ChatListModel;
import com.example.dalbo.lazychat.Service.NotifService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dalbo on 11/11/2016.
 */

public class ChatActivity extends BaseActivity {
    RecyclerView recChatList;
    EditText iMsg;
    TextView roomTitle;
    Button bSend;
    DatabaseReference dbRef;
    DatabaseReference dbRoomRef;
    DatabaseReference dbChatRef;
    String roomKey, sroomTitle, sroomExpDate, sroomDesc;
    ChatListAdapter adapter;
    ActionBar actionBar;
    public static int started;
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatview_layout);
        Config.prefInit(ChatActivity.this);
        started = 1;
        actionBar = getSupportActionBar();
        bSend = (Button) findViewById(R.id.bkirim);
        recChatList = (RecyclerView) findViewById(R.id.recchatlist);
        iMsg = (EditText) findViewById(R.id.imsg);
        roomKey = getIntent().getExtras().getString("key");
        dbRef = FirebaseDatabase.getInstance().getReference().child(Config.DBNAME);
        adapter = new ChatListAdapter();
        dbRoomRef = dbRef.child(roomKey);
        dbChatRef = dbRoomRef.child("data");
        recChatList.setLayoutManager(new LinearLayoutManager(this));
        recChatList.setAdapter(adapter);
        dbRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sroomTitle = dataSnapshot.child("roomname").getValue().toString();
                sroomDesc = dataSnapshot.child("roomdesc").getValue().toString();
                sroomExpDate = dataSnapshot.child("expdate").getValue().toString();
                actionBar.setTitle(sroomTitle);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatListModel model = dataSnapshot.getValue(ChatListModel.class);
                adapter.addNewData(model);
                recChatList.scrollToPosition(adapter.getItemCount() - 1);
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
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.prefInit(ChatActivity.this);
                if (!iMsg.getText().toString().isEmpty()) {
                    ChatListModel model = new ChatListModel(Config.getNick(), iMsg.getText().toString(), Config.getNowDate(), Config.getEmail());
                    dbChatRef.push().setValue(model.toMap());
                    iMsg.setText("");
                    iMsg.requestFocus();
                }
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
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        switchCompat = (SwitchCompat) menu.findItem(R.id.notifopt).getActionView();
        if (Config.getNotifList().contains(roomKey)) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Config.addNotifList(roomKey);
                } else {
                    Config.removeNotifList(roomKey);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
