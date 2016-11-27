package com.example.dalbo.lazychat.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dalbo on 11/11/2016.
 */

public class ChatListModel {
    String nick, msg;
    String time;
    String email;

    public ChatListModel() {
    }

    public ChatListModel(String nick, String msg, String time, String email) {
        this.nick = nick;
        this.msg = msg;
        this.time = time;
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, String> toMap(){
        Map<String, String> temp = new HashMap<>();
        temp.put("nick",this.nick);
        temp.put("msg",this.msg);
        temp.put("time",this.time);
        temp.put("email",this.email);
        return temp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
