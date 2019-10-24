package com.marannix.android.cadence.dagger.modules

import android.app.Application
import androidx.room.Room
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.database.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    /**
     *
     *  Cannot access database on the main thread since it may potentially lock the UI for a long period of time so I used allowMAinThreadQueries
     *  This should be removed when Livedata is implemented
     */
    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): ApplicationDatabase {
        return Room.databaseBuilder(
            application,
            ApplicationDatabase::class.java, "githubrepo.db"
        )
            .allowMainThreadQueries()
            .build()
    }

    // TODO: Create a test database which can be used for unit testing
//    @Singleton
//    @Provides
//    fun provideTestRoomDatabase(application: Application): ApplicationDatabase {
//        return Room.inMemoryDatabaseBuilder(
//            application,
//            ApplicationDatabase::class.java
//        )
//            .allowMainThreadQueries()
//            .build()
//    }

    @Singleton
    @Provides
    fun providesGithubRepoDao(applicationDatabase: ApplicationDatabase): GithubRepoDao {
        return applicationDatabase.githubRepoDao()
    }
}