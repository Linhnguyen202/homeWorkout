package com.example.homeworkout.data.local.room.repository

import androidx.lifecycle.LiveData
import com.example.homeworkout.data.local.room.dao.UserDao
import com.example.homeworkout.data.local.room.dao.WorkoutDao
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.room.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val userDao: UserDao,
    private val workoutDao: WorkoutDao
) {
    suspend fun addUser(user : User) : Long  = userDao.addUser(user)

    suspend fun updateUser(name : String,age : String, id : String) = userDao.updateUser(name, age, id)

   fun getUser() = userDao.getAllUser()

    fun checkUserExist(id : String) = userDao.isExists(id)


    suspend fun addDoneWorkout(workout: DoneWorkout) = workoutDao.addDoneWorkout(workout)


    fun getDoneWorkout(userId : String) = workoutDao.getDoneWorkout(userId)

    fun getDailyWorkout(userId : String,date: String) = workoutDao.getDailyWorkout(userId,date)
}