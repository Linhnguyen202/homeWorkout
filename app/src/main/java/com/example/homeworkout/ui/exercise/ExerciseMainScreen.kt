package com.example.homeworkout.ui.exercise

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.remote.model.Exercise
import com.example.homeworkout.databinding.ActivityExerciseMainScreenBinding
import com.example.homeworkout.databinding.RestLayoutBinding
import dagger.android.support.DaggerAppCompatActivity
import java.util.Locale


class ExerciseMainScreen : DaggerAppCompatActivity() {
    lateinit var binding: ActivityExerciseMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseMainScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }

}