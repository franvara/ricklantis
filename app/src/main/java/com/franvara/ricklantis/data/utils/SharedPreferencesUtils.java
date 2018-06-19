package com.franvara.ricklantis.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    /**
     * SP default name
     */
    private static final String SP_FILE = "ricklantisPreferences";

    /**
     * SP Show nav extra label KEY
     */
    public static final String NEXT_PAGE = "next_page";

    private SharedPreferencesUtils() {
    }

    /**
     * Method to store the value of a boolean preference.
     * @param context
     * @param key The pref key.
     * @param value The value to store.
     */
    public static void storeIntPref(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Method to retrieve any string by key from SharedPreferences
     * @param context
     * @param key
     * @return
     */
    public static int getIntPref(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    /**
     * Method to Store any string with its own key on SharedPreferences
     * @param context
     * @param key
     * @param value
     */
    public static void storeData(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * Method to retrieve any string by key from SharedPreferences
     * @param context
     * @param key
     * @return
     */
    public static String getData(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);

        if (sp.contains(key))
            return sp.getString(key, null);

        return null;
    }

    /**
     * Method to delete key stored in SharedPreferences
     * @param context
     * @param key
     * @return
     */
    public static void clearData(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();

    }

    /**
     * Method to retrieve the value of a boolean preference.
     *
     * @param context
     * @param key The pref key.
     * @return The preference value, or the default value if not set.
     */
    public static boolean getBooleanPref(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);

        return sp.getBoolean(key, true);
    }


    /**
     * Mehtod to store the value of a boolean preference.
     * @param context
     * @param key The pref key.
     * @param value The value to store.
     */
    public static void storeBooleanPref(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
}
