package com.example.homeworkout.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkout.di.viewmodel.ViewModelFactory
import com.example.homeworkout.di.viewmodel.ViewModelKey
import com.example.homeworkout.ui.exercise.DoneWorkoutViewModel
import com.example.homeworkout.ui.login.AuthViewModel
import com.example.homeworkout.ui.main.dailyFragment.DailyViewModel
import com.example.homeworkout.ui.main.homeFragment.HomeViewModel
import com.example.homeworkout.ui.main.profileFragment.EditProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun mainViewModel(viewModel: AuthViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DoneWorkoutViewModel::class)
    internal abstract fun doneWorkoutViewModel(viewModel: DoneWorkoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DailyViewModel::class)
    internal abstract fun dailyViewModel(viewModel: DailyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    internal abstract fun editProfileViewModel(viewModel: EditProfileViewModel): ViewModel



    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}