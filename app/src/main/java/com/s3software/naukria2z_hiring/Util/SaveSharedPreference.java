package com.s3software.naukria2z_hiring.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
        public static final String LOGGED_IN_PREF = "logged_in_status";

static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context); }

public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
        }

public static boolean getLoggedStatus(Context context) {
    return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
}}