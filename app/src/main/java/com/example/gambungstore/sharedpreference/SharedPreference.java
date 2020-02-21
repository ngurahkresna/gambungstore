package com.example.gambungstore.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    static final String KEY_USER_TEREGISTER ="user_id";
    static final String KEY_TOKEN_TEREGISTER="token_id";

    public static SharedPreferences getSharedPreferences(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("gambung", Context.MODE_PRIVATE);
        return sharedpreferences;
    }

    //id
    public static void setRegisteredId(Context context, int id){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(KEY_USER_TEREGISTER, id);
        editor.apply();
    }

    public static int getRegisteredId(Context context){
        return getSharedPreferences(context).getInt(KEY_USER_TEREGISTER,0);
    }

    public static void clearRegisteredId (Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_TOKEN_TEREGISTER);
        editor.clear();
        editor.commit();
    }

    //token
    public static void setRegisteredToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_TOKEN_TEREGISTER, token);
        editor.apply();
    }

    public static String getRegisteredToken(Context context){
        return getSharedPreferences(context).getString(KEY_TOKEN_TEREGISTER,"");
    }

    public static void clearRegisteredToken(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_TOKEN_TEREGISTER);
        editor.clear();
        editor.commit();
    }

}
