package com.example.homeworkout.ui.exercise

import androidx.lifecycle.ViewModel
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.local.room.repository.LocalRepository
import javax.inject.Inject

class DoneWorkoutViewModel  @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel(){

    suspend fun saveDoneWorkout(workout : DoneWorkout) : Long = localRepository.addDoneWorkout(workout)
}