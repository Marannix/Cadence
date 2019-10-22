package com.marannix.android.cadence.dagger.modules

import androidx.fragment.app.FragmentActivity
import com.marannix.android.cadence.activity.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideMainActivity(activity: MainActivity): FragmentActivity
}