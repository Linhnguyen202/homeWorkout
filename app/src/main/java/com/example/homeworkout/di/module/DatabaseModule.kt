package com.example.homeworkout.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.example.homeworkout.data.local.room.WorkoutDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun getInstance(context: Context): WorkoutDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WorkoutDatabase::class.java,
            "workoutDatabase"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUser(db : WorkoutDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideWorkout(db : WorkoutDatabase) = db.workoutDao()

    @Singleton
    @Provides
    fun provideAppPreference(appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("app",Context.MODE_PRIVATE)
    }
}