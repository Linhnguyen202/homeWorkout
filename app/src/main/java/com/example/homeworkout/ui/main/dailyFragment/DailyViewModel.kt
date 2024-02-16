package com.example.homeworkout.ui.main.dailyFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.local.room.repository.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel(){
    val listDoneWorkout : MutableLiveData<List<DoneWorkout>> = MutableLiveData()
    fun getDoneWorkoutData(userId : String) = CoroutineScope(Dispatchers.IO).launch {
        listDoneWorkout.postValue(localRepository.getDoneWorkout(userId))
    }

    fun getDailyWorkout(userId : String,date: String) = CoroutineScope(Dispatchers.IO).launch {
        listDoneWorkout.postValue(localRepository.getDailyWorkout(userId,date))
    }
}