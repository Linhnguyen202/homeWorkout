package com.example.homeworkout.data.local.room.converter

import androidx.room.TypeConverter
import com.example.homeworkout.data.remote.model.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun mapListToString(value: Workout): String {
        val gson = Gson()
        val type = object : TypeToken<Workout>() {}.type
        return gson.toJson(value, type)
    }
    @TypeConverter
    fun mapStringToList(value: String): Workout {
        val gson = Gson()
        val type = object : TypeToken<Workout>() {}.type
        return gson.fromJson(value, type)
    }
}