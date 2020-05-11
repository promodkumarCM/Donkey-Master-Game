package com.sparklingapps.cardgamefrog.utils;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Basil1112  on 01,November,2018
 */
public class PreferenceController {
    private static final String TAG = "PreferenceController";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    public PreferenceController(Context mContext) {
        this.mContext = mContext;
        initVariable();
    }

    private void initVariable() {
        mSharedPreferences = mContext.getSharedPreferences(AppConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public boolean setInteger(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
        return true;
    }

    public int getInteger(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public boolean setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
        return true;
    }
    public boolean setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
        return true;
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public boolean isContains(String key) {
        return mSharedPreferences.contains(key);
    }


}
