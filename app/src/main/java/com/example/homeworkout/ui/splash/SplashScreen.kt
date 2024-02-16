package com.example.homeworkout.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.homeworkout.R
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.databinding.ActivitySplashScreenBinding
import com.example.homeworkout.ui.main.MainScreen
import com.example.homeworkout.ui.onBoard.OnBoardScreen
import com.example.homeworkout.ui.onBoard.ViewPagerAdpater
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashScreen : DaggerAppCompatActivity() {
    lateinit var binding : ActivitySplashScreenBinding
    @Inject lateinit var sharePreferenceUtils: sharePreferenceUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.myLooper()!!).postDelayed({
            if(sharePreferenceUtils.isSharedPreferencesExist(this,"app","USER_VALUE")){
                startActivity(Intent(this,MainScreen::class.java))
                finish()
            }
            else{
                startActivity(Intent(this,OnBoardScreen::class.java))
                finish()

            }

        },3000)

    }
}