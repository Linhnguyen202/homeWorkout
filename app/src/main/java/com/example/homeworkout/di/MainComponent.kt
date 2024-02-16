package com.example.homeworkout.di

import androidx.fragment.app.FragmentActivity
import com.example.homeworkout.di.module.ActivityBindingModule
import com.example.homeworkout.di.module.AppModule
import com.example.homeworkout.di.module.DatabaseModule
import com.example.homeworkout.di.module.ViewModelModule
import com.example.homeworkout.di.scope.ActivityScope
import com.example.homeworkout.ui.login.LoginScreen
import com.example.homeworkout.ui.login.RegisterLayout.LevelFragment
import com.example.homeworkout.ui.main.MainScreen
import com.example.homeworkout.ui.main.homeFragment.HomeFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [])
public interface MainComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create() : MainComponent
    }
}