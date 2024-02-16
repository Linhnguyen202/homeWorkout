package com.example.homeworkout.data.local.share

import android.content.Context
import android.content.SharedPreferences
import com.example.homeworkout.data.local.room.model.User
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class sharePreferenceUtils @Inject constructor(val preferences: SharedPreferences) {
    fun isSharedPreferencesExist(context: Context, sharedPreferencesName: String, key: String): Boolean {
//        val sharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        return preferences.contains(key)
    }
    fun saveUser(user: User, context: Context) {
//        val preferences =  context.getSharedPreferences("USER", Context.MODE_PRIVATE)
        val gson = Gson()
        val user = gson.toJson(user)
        preferences.edit().putString("USER_VALUE",user).apply()
    }
    fun getUser(context: Context): User {
        val gson = Gson()
        val user = preferences.getString("USER_VALUE", null)
        return gson.fromJson(user, User::class.java)
    }
    fun removeUser(context: Context) {
        val editor = preferences.edit()
        editor.remove("USER_VALUE")
        editor.apply()
    }
}