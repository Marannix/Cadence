package com.marannix.android.cadence.dagger.modules

import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.repositories.GithubRepoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGithubRepoRepository(githubRepoDao: GithubRepoDao): GithubRepoRepository {
        return GithubRepoRepository(githubRepoDao)
    }
}