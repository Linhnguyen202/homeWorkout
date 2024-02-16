package com.example.homeworkout.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.local.room.repository.LocalRepository
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel(){

    suspend fun addUser(user : User) : Long = localRepository.addUser(user)

    fun checkUser(id : String) : Boolean = localRepository.checkUserExist(id)
}