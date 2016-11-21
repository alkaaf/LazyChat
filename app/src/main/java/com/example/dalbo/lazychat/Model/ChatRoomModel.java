package com.example.dalbo.lazychat.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dalbo on 11/11/2016.
 */

public class ChatRoomModel {
    String key;
    String roomname, roomdesc;
    String expdate;
    String expired;

    public ChatRoomModel() {
    }

    public ChatRoomModel(String roomname, String roomdesc, String expdate, String expired) {

        this.roomname = roomname;
        this.roomdesc = roomdesc;
        this.expdate = expdate;
        this.expired = expired;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomdesc() {
        return roomdesc;
    }

    public void setRoomdesc(String roomdesc) {
        this.roomdesc = roomdesc;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public Map<String, String> toMap(){
        Map<String, String> temp = new HashMap<>();
        temp.put("roomname",roomname);
        temp.put("roomdesc",roomdesc);
        temp.put("expdate",expdate);
        temp.put("expired",expired);
        return temp;
    }
}
