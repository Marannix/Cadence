package com.marannix.android.cadence.dagger.modules

import androidx.fragment.app.FragmentActivity
import com.marannix.android.cadence.activity.MainActivity
import com.marannix.android.cadence.fragment.GithubRepoFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideMainActivity(activity: MainActivity): FragmentActivity

    @ContributesAndroidInjector
    abstract fun githubRepoFragment(): GithubRepoFragment
}