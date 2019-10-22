package com.marannix.android.cadence.dagger.components

import android.app.Application
import com.marannix.android.cadence.MainApplication
import com.marannix.android.cadence.dagger.modules.ActivityBuilder
import com.marannix.android.cadence.dagger.modules.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ActivityBuilder::class,
        ApiModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: MainApplication)
}