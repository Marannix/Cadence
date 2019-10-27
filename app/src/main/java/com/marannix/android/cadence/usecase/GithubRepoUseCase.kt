package com.marannix.android.cadence.usecase

import com.marannix.android.cadence.repositories.GithubRepoRepository
import com.marannix.android.cadence.repositories.GithubRepoRepository.GitHubRepoDataState
import io.reactivex.Observable
import javax.inject.Inject

class GithubRepoUseCase @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) {

    fun getGithubRepoDataState(): Observable<GitHubRepoDataState> {
        return githubRepoRepository.getGithubRepos()
            .map<GitHubRepoDataState> { list ->
                GitHubRepoDataState.Success(list)
            }
            .toObservable()
            .onErrorReturn { GitHubRepoDataState.Error(it) }
    }
}