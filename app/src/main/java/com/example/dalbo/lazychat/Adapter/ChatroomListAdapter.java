package com.example.dalbo.lazychat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dalbo.lazychat.ChatActivity;
import com.example.dalbo.lazychat.MainActivity;
import com.example.dalbo.lazychat.Model.ChatRoomModel;
import com.example.dalbo.lazychat.R;
import com.example.dalbo.lazychat.SettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dalbo on 11/11/2016.
 */

public class ChatroomListAdapter extends RecyclerView.Adapter<ChatroomListAdapter.viewHolder> {
    Activity activity;
    Context context;
    List<ChatRoomModel> data;
    public ChatroomListAdapter(Activity activity) {
        this.activity = activity;
        this.data = new ArrayList<>();
    }

    public void addNewData(ChatRoomModel data){
        this.data.add(data);
        notifyDataSetChanged();
    }

    @Override
    public ChatroomListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_chatroomlist,parent,false);
        context = parent.getContext();
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatroomListAdapter.viewHolder holder, int position) {
        holder.oRoomName.setText(data.get(position).getRoomname());
        holder.oRoomDesc.setText(data.get(position).getRoomdesc());
        holder.oRoomExp.setText(data.get(position).getExpdate());
        final String key = data.get(position).getKey();
        holder.trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(context,ChatActivity.class);
                chat.putExtra("key",key);
                activity.startActivity(chat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class viewHolder extends RecyclerView.ViewHolder{
        TextView oRoomName, oRoomDesc, oRoomExp;
        RelativeLayout trigger;
        public viewHolder(View itemView) {
            super(itemView);
            oRoomName = (TextView)itemView.findViewById(R.id.oroomname);
            oRoomDesc = (TextView)itemView.findViewById(R.id.oroomdesc);
            oRoomExp = (TextView)itemView.findViewById(R.id.oroomexp);
            trigger = (RelativeLayout)itemView.findViewById(R.id.trigger);
        }
    }
}
