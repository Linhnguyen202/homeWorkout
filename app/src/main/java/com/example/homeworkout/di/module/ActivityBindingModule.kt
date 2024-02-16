package com.example.homeworkout.di.module

import com.example.homeworkout.ui.exercise.ExerciseMainScreen
import com.example.homeworkout.ui.exercise.doneFragment.DoneFragment
import com.example.homeworkout.ui.exercise.praciteFragment.PracticeFragment
import com.example.homeworkout.ui.login.LoginScreen
import com.example.homeworkout.ui.login.RegisterLayout.LevelFragment
import com.example.homeworkout.ui.login.SignInLayout.SignInFragment
import com.example.homeworkout.ui.main.MainScreen
import com.example.homeworkout.ui.main.dailyFragment.DailyFragment
import com.example.homeworkout.ui.main.detailFragment.DetailFragment
import com.example.homeworkout.ui.main.homeFragment.HomeFragment
import com.example.homeworkout.ui.main.homeFragment.WorkoutListFragment
import com.example.homeworkout.ui.main.profileFragment.EditProfileFragment
import com.example.homeworkout.ui.main.profileFragment.ProfileFragment
import com.example.homeworkout.ui.splash.SplashScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity() : SplashScreen

    @ContributesAndroidInjector
    abstract fun bindLoginActivity() : LoginScreen


    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainScreen

    @ContributesAndroidInjector
    abstract fun bindingExerciseMainActivity() : ExerciseMainScreen


    @ContributesAndroidInjector
    abstract fun homeScreenFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun workoutListScreenFragment() : WorkoutListFragment

    @ContributesAndroidInjector
    abstract fun dailyScreenFragment() : DailyFragment

    @ContributesAndroidInjector
    abstract fun profileScreenFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun EditProfileScreenFragment() : EditProfileFragment

    @ContributesAndroidInjector
    abstract fun signInFragment() : SignInFragment

    @ContributesAndroidInjector
    abstract fun levelScreenFragment() : LevelFragment

    @ContributesAndroidInjector
    abstract fun detailSreenFragment() : DetailFragment


    @ContributesAndroidInjector
    abstract fun praceticeScreenFragment() : PracticeFragment

    @ContributesAndroidInjector
    abstract fun doneScreenFragment() : DoneFragment


}