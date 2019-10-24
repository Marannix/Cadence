package com.marannix.android.cadence.dagger.modules

import com.marannix.android.cadence.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    /**
     * Houses all Activities and Fragment Modules.
     * (Only one Activity but still... )
     */
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
}