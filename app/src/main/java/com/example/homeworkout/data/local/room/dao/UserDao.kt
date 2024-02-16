package com.example.homeworkout.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homeworkout.data.local.room.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User) : Long

    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Query("SELECT EXISTS(SELECT * FROM User where id IN (:userId) )")
    fun isExists(userId : String): Boolean

    @Query("UPDATE user SET name = :name, old= :age WHERE id =:id")
    fun updateUser(name: String?, age: String?, id: String)
}