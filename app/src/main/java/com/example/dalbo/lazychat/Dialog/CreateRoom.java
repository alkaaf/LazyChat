package com.example.dalbo.lazychat.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dalbo.lazychat.Config;
import com.example.dalbo.lazychat.MainActivity;
import com.example.dalbo.lazychat.Model.ChatRoomModel;
import com.example.dalbo.lazychat.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by dalbo on 11/11/2016.
 */

public class CreateRoom extends Dialog {
    EditText roomName, roomDesc, expDate;
    Button bCreate;
    DatabaseReference dbRef;

    public CreateRoom(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_newroom);
        roomName = (EditText) findViewById(R.id.inewroomname);
        roomDesc = (EditText) findViewById(R.id.inewroomdesc);
        expDate = (EditText) findViewById(R.id.iexpdate);
        bCreate = (Button) findViewById(R.id.bcreateroom);
        dbRef = FirebaseDatabase.getInstance().getReference().child(Config.DBNAME);
        expDate.setOnClickListener(new View.OnClickListener() {
            Calendar calendar;
            DatePickerDialog dpd;

            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                dpd = new DatePickerDialog(CreateRoom.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        expDate.setText(i + "/" + i1 + "/" + i2);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatRoomModel model = new ChatRoomModel(roomName.getText().toString(), roomDesc.getText().toString(), expDate.getText().toString(),"0");
                dbRef.push().setValue(model.toMap());
                CreateRoom.this.dismiss();
            }
        });
    }
}
