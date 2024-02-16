package com.example.homeworkout.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homeworkout.data.local.room.converter.Converter
import com.example.homeworkout.data.local.room.dao.UserDao
import com.example.homeworkout.data.local.room.dao.WorkoutDao
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.remote.model.Workout


@Database(entities = [User::class,DoneWorkout::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun workoutDao() : WorkoutDao
}