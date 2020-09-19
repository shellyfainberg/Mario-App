package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySp {

    public interface KEYS {
        public static final String CURRENT_WINNER = "CURRENT_WINNER";
        public static final String TOP_TEN = "TOP_TEN";
    }

    private SharedPreferences prefs;

    public MySp(Context context) {
        prefs = context.getSharedPreferences("MY_SP", MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {

        return prefs.getString(key, def);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public boolean getBoolean(String key, boolean def) {

        return prefs.getBoolean(key, def);
    }

    public int getInt(String key, int def) {

        return prefs.getInt(key, def);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public float getDouble(String key, double def) {
        return prefs.getLong(key, Double.doubleToRawLongBits(def));
    }

    public void putDouble(String key, double value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key,  Double.doubleToRawLongBits(value));
        editor.apply();
    }
}


