package com.example.homeworkout.di.module

import android.content.Context
import androidx.room.Room
import com.example.homeworkout.data.local.room.WorkoutDatabase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Singleton
    @Provides
    fun getInstanceAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}