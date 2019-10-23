package com.marannix.android.cadence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel

@Database(
    entities = [GitHubRepoModel::class],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

}