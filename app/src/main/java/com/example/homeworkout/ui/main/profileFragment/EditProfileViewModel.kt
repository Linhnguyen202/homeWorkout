package com.example.homeworkout.ui.main.profileFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.room.repository.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel(){

    suspend fun updateUser(name : String, age : String, id : String) = localRepository.updateUser(name,age, id)
}