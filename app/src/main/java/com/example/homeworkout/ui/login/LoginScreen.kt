package com.example.homeworkout.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkout.MyApplication
import com.example.homeworkout.R
import com.example.homeworkout.databinding.ActivityLoginScreenBinding
import com.example.homeworkout.di.MainComponent
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerAppCompatActivity

class LoginScreen : DaggerAppCompatActivity() {
    lateinit var binding : ActivityLoginScreenBinding
//    public lateinit var component : MainComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        component = (application as MyApplication).component.getMainCompent().create()
//        component.injectAuthActivity(this)
    }
}