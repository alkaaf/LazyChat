package com.example.dalbo.lazychat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by dalbo on 11/11/2016.
 */

public class Config {
    public static final String DBNAME = "LazyChat";
    static SharedPreferences sp;

    public static String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.DAY_OF_MONTH);
        return now;
    }

    public static void prefInit(Context c) {
        sp = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static String getNick() {
        return sp.getString("nick", "Guest");
    }

    public static void setNick(String nick) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nick", nick);
        editor.apply();

    }
    public static void setToken(String token){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }
    public  static String getToken(){
        return sp.getString("token",null);

    }

    public static void setEmail(String email){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email",email);
        editor.apply();
    }
    public static String getEmail(){
        return sp.getString("email",null);
    }
}
