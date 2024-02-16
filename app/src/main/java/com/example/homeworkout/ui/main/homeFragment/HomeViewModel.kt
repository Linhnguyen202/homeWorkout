package com.example.homeworkout.ui.main.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.local.room.repository.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel@Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel(){
    val listUser : MutableLiveData<List<User>> = MutableLiveData()
   fun getUserData() = CoroutineScope(Dispatchers.IO).launch {
       listUser.postValue(localRepository.getUser())
   }
}