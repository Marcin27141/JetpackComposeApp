package com.example.jetpackcomposeapp.services

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class PreferencesManager {
    private val DEFAULT_PREFS_NAME = "my_prefs"
    companion object {
        private var INSTANCE: PreferencesManager? = null
        fun getInstance(): PreferencesManager {
            if (INSTANCE == null)
                INSTANCE = PreferencesManager()
            return INSTANCE as PreferencesManager
        }
    }

    fun getNameAndNick(context: Context, defaultName: String = "Marcin Tkocz", defaultNick: String = "Nick") : Pair<String, String> {
        val data: SharedPreferences = context.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        return Pair(data.getString("name", null) ?: defaultName, data.getString("nick", null) ?: defaultNick);
    }

    fun setNameAndNick(name: String, nick: String, context: Context) {
        val data: SharedPreferences = context.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("name", name)
        editor.putString("nick", nick)
        editor.apply()
    }
    fun setHomeImage(imageUri: Uri, context: Context) {
        val data: SharedPreferences = context.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("imageUri", imageUri.toString())
        editor.apply()
    }
    fun getHomeImageUri(context: Context) : Uri? {
        val data: SharedPreferences = context.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val uriString = data.getString("imageUri", null)
        return if (uriString == null) null else Uri.parse(uriString)
    }
}