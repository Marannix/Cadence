package com.marannix.android.cadence.dagger.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

// TODO: Might delete just testing dagger @Provides (May need context in future)
@Module
class ApplicationModule {

    @Provides
    internal fun provideContext(application: Application): Context {
        return application
    }
}