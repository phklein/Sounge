package com.sounge.soungeapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferencesUtils {
    object Constants {
        const val USER_INFO_PREFS = "userInfoPreferences"

        const val USER_LOGIN_KEY = "userLogin"
    }

    fun get(c: Context, pref: String, key: String): String {
        val prefs = c.getSharedPreferences(pref, MODE_PRIVATE)
        return prefs.getString(key, "")!!
    }

    fun exists(c: Context, pref: String, key: String): Boolean {
        val prefs = c.getSharedPreferences(pref, MODE_PRIVATE)
        return prefs.contains(key)
    }

    fun put(c: Context, pref: String, key: String, value: String) {
        val prefs = c.getSharedPreferences(pref, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }
}