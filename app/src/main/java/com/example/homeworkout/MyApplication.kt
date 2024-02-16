package com.example.homeworkout

import android.app.Application
import com.example.homeworkout.di.AppComponent
import com.example.homeworkout.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.Objects
import javax.inject.Inject


class MyApplication : DaggerApplication()  {

    lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()
//       component= DaggerAppComponent.builder().application(this).build()
//        component.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component= DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }


}