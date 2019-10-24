package com.marannix.android.cadence.usecase

import androidx.lifecycle.LiveData
import com.marannix.android.cadence.model.GitHubRepoState
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GithubRepoUseCase @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) {

    private fun getGithubRepos(): Single<GitHubRepoState> {
        return githubRepoRepository.getGithubRepos().map {
            GitHubRepoState.Success(it)
        }
    }

    fun handleGitHubRepoState(): Observable<GitHubRepoState> {
        return getGithubRepos()
            .toObservable()
            .startWith(GitHubRepoState.Loading)
            .onErrorReturn { GitHubRepoState.Error(it) }
    }

    fun getStoredGithubRepos(): LiveData<List<GitHubRepoModel>> {
        return githubRepoRepository.getAllGithubRepos()
    }
}