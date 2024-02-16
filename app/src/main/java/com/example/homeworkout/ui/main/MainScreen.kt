package com.example.homeworkout.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.homeworkout.MyApplication
import com.example.homeworkout.R
import com.example.homeworkout.databinding.ActivityMainScreenBinding
import com.example.homeworkout.di.MainComponent
import dagger.android.support.DaggerAppCompatActivity

class MainScreen : DaggerAppCompatActivity() {
    lateinit var binding : ActivityMainScreenBinding
//    lateinit var component: MainComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        component = (application as MyApplication).component.getMainCompent().create()
//        component.injectMainActivity(this)
        var controller =findNavController(R.id.containerFragment)
        binding.bottomNavigation.setupWithNavController(controller)
    }
}