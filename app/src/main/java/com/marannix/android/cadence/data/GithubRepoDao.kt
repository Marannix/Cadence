package com.marannix.android.cadence.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marannix.android.cadence.model.GitHubRepoModel

@Dao
interface GithubRepoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGithubRepos(githubRepos: List<GitHubRepoModel>)

    @Query("select * from repos")
    fun getAllGithubRepos(): LiveData<List<GitHubRepoModel>>
}