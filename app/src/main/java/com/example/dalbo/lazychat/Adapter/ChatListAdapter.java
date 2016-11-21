package com.example.dalbo.lazychat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dalbo.lazychat.Model.ChatListModel;
import com.example.dalbo.lazychat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dalbo on 11/11/2016.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.viewHolder> {
    List<ChatListModel> data;

    public ChatListAdapter() {
        data = new ArrayList<>();
    }

    public void addNewData(ChatListModel model) {
        this.data.add(model);
        notifyDataSetChanged();
    }

    @Override
    public ChatListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_chatlist, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.viewHolder holder, int position) {
        holder.oNick.setText(data.get(position).getNick());
        holder.oMsg.setText(data.get(position).getMsg());
        holder.oTime.setText(data.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView oMsg, oNick, oTime;

        public viewHolder(View itemView) {
            super(itemView);
            oMsg = (TextView) itemView.findViewById(R.id.omsg);
            oNick = (TextView) itemView.findViewById(R.id.onick);
            oTime = (TextView) itemView.findViewById(R.id.otime);
        }
    }
}
