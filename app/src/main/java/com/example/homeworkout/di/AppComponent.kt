package com.example.homeworkout.di

import android.app.Application
import com.example.homeworkout.MyApplication
import com.example.homeworkout.di.module.ActivityBindingModule
import com.example.homeworkout.di.module.AppModule
import com.example.homeworkout.di.module.DatabaseModule
import com.example.homeworkout.di.module.FirebaseModule
import com.example.homeworkout.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,AppModule::class,DatabaseModule::class,ViewModelModule::class,ActivityBindingModule::class])
interface AppComponent : AndroidInjector<MyApplication> {

    override fun inject(application: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}