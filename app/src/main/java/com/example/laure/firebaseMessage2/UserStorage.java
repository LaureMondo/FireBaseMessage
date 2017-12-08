package com.example.laure.firebaseMessage2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Laure on 04/12/2017.
 */

public class UserStorage {

    public static final String USER_NAME = "USER_NAME";
    public static final String USER_EMAIL = "USER_EMAIL";

    // ne jamais stocker context en static -> pb de mémoire
    public static void saveUserInfo(Context context, String name, String email) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        // key -> value
        editor.putString(USER_NAME, name);
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public static boolean isLogged(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_EMAIL, null) != null;
    }

    public static User getUser(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new User(sharedPreferences.getString(USER_NAME, ""), sharedPreferences.getString(USER_EMAIL, ""));
    }

}
