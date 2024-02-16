package com.example.homeworkout.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.room.model.User

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDoneWorkout(workout: DoneWorkout) : Long

    @Query("SELECT * FROM DoneWorkout where idUser IN (:userId)")
    fun getDoneWorkout(userId : String): List<DoneWorkout>

    @Query("SELECT * FROM DoneWorkout where idUser IN (:userId) and date in (:date)")
    fun getDailyWorkout(userId : String,date : String): List<DoneWorkout>
}