package com.dalamsyah.siaprint.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.dalamsyah.siaprint.models.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "com.dalamsyah.siaprint.pref"

        private const val KEY_MY_BOOLEAN = "my_boolean"
        private const val KEY_MY_ARRAY = "string_array"

        private const val USER = "$PREFS_FILENAME.user"
        private const val API_TOKEN = "$PREFS_FILENAME.apitoken"
    }
    private val gson = Gson()
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var apiToken: String
        get() = sharedPrefs.getString(API_TOKEN, "") ?: ""
        set(value) = sharedPrefs.edit { putString(API_TOKEN, value) }

    var myBoolean: Boolean
        get() = sharedPrefs.getBoolean(KEY_MY_BOOLEAN, false)
        set(value) = sharedPrefs.edit { putBoolean(KEY_MY_BOOLEAN, value) }

    var myStringArray: Array<String>
        get() = sharedPrefs.getStringSet(KEY_MY_ARRAY, emptySet())?.toTypedArray()?: emptyArray()
        set(value) = sharedPrefs.edit { putStringSet(KEY_MY_ARRAY, value.toSet()) }

    var user: Users?
        get() {
            val jsonString = sharedPrefs.getString(USER, null) ?: return null
            return gson.fromJson(jsonString, object : TypeToken<Users>() {}.type)
        }
        set(value) = sharedPrefs.edit { putString(USER, gson.toJson(value)) }

}