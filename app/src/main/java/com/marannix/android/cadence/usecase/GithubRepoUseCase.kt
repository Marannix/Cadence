package com.marannix.android.cadence.usecase

import com.marannix.android.cadence.repositories.GithubRepoRepository
import com.marannix.android.cadence.repositories.GithubRepoRepository.GithubRepoDataState
import io.reactivex.Observable
import javax.inject.Inject

class GithubRepoUseCase @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) {

    fun getGithubRepoDataState(): Observable<GithubRepoDataState> {
        return githubRepoRepository.getGithubRepos()
            .map<GithubRepoDataState> { list ->
                GithubRepoDataState.Success(list)
            }
            .onErrorReturn { error -> GithubRepoDataState.Error(error) }
    }
}