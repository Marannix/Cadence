package com.marannix.android.cadence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel

@Database(
    entities = [GitHubRepoModel::class],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

    object DatabaseProvider {
        private var instance: ApplicationDatabase? = null
        var TEST_MODE = false
        fun getInstance(context: Context): ApplicationDatabase? {
            if (instance == null) {
                synchronized(ApplicationDatabase::class) {
                    instance = if (TEST_MODE) {
                        buildTestDatabase(context)
                    } else {
                        buildDatabase(context)
                    }
                }
            }
            return instance
        }

        private fun buildDatabase(context: Context): ApplicationDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java, "githubrepo.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        private fun buildTestDatabase(context: Context): ApplicationDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}