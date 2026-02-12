package com.mas.flowlibre.data.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mas.flowlibre.data.model.UserDto

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_USER = "user_data"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"

        private const val KEY_ACCESS_TOKEN = "access_token"

        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_TOKEN = "auth_token"
    }

    fun saveUser(user: UserDto) {
        val userJson = gson.toJson(user)
        prefs.edit()
            .putString(KEY_USER, userJson)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }


    fun getUser(): UserDto? {
        val userJson = prefs.getString(KEY_USER, null)
        return if (userJson != null) {
            gson.fromJson(userJson, UserDto::class.java)
        } else {
            null
        }
    }


    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false) && getUser() != null
    }


    fun clearSession() {
        prefs.edit()
            .remove(KEY_USER)
            .remove(KEY_IS_LOGGED_IN)
            .remove(KEY_ACCESS_TOKEN)
            .remove(KEY_REFRESH_TOKEN)
            .remove(KEY_TOKEN)
            .apply()
    }

    fun getToken(): String? {
        return getAccessToken()
    }

    fun saveTokens(access_token: String, refresh_token: String) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, access_token)
            .putString(KEY_REFRESH_TOKEN, refresh_token)
            .apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }


}