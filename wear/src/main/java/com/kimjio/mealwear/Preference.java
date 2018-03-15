package com.kimjio.mealwear;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private SharedPreferences mPref;

    public Preference(Context mContext) {
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public Preference(Context mContext, String prefName) {
        mPref = mContext.getSharedPreferences(prefName, 0);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public String getString(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public void putInt(String key, int value) {
        mPref.edit().putInt(key, value).apply();
    }

    public void putString(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void remove(String key) {
        mPref.edit().remove(key).apply();
    }
}