package com.am.reachwell.Global.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.am.reachwell.Global.Depedencies.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by udaypatil on 21/02/18.
 */

@Singleton
public class SharedPreferenceHelper {

    public static String PREF_KEY_ACCESS_TOKEN = "access-token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEditor;
    private Context context;

    @Inject
    public SharedPreferenceHelper(@ApplicationContext Context context) {
        this.context = context;
    }

    public void setSharedPreferences(String name, int mode){
        this.sharedPreferences = context.getSharedPreferences(name,mode);
    }

    public void setSharedPreferencesEditor(String name, int mode){
        this.sharedPreferences = context.getSharedPreferences(name,mode);
        this.sharedPreferenceEditor = sharedPreferences.edit();
    }

    public void putEditor(String key, String value){
        sharedPreferenceEditor.putString(key,value);
    }

    public void putEditor(String key, int value){
        sharedPreferenceEditor.putInt(key,value);
    }

    public void putEditor(String key, boolean value){
        sharedPreferenceEditor.putBoolean(key,value);
    }
    public void commitEditor(){
        sharedPreferenceEditor.commit();
    }

    public void put(String key, String value) {
        sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putString(key, value);
        sharedPreferenceEditor.apply();
    }

    public void put(String key, int value) {
        sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putInt(key, value);
        sharedPreferenceEditor.apply();
    }

    public void put(String key, float value) {
        sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putFloat(key, value);
        sharedPreferenceEditor.apply();
    }

    public void put(String key, boolean value) {
        sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putBoolean(key, value);
        sharedPreferenceEditor.apply();
    }

    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public Integer get(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public Float get(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public Boolean get(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void deleteSavedData(String key) {
        sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.remove(key);
        sharedPreferenceEditor.apply();
    }
}