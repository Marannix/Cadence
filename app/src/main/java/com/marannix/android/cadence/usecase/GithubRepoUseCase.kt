package com.marannix.android.cadence.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marannix.android.cadence.model.GitHubRepoState
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class GithubRepoUseCase @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) {

    private val liveData = MutableLiveData<List<GitHubRepoModel>>()

    fun handleGitHubRepoState(): Observable<GitHubRepoState> {
        return getGithubRepos()
            .toObservable()
            .startWith(GitHubRepoState.Loading)
            .onErrorReturn { GitHubRepoState.Error(it) }
    }

    private fun getGithubRepos(): Flowable<GitHubRepoState> {
        return githubRepoRepository.getGithubRepos()
            .map {
                liveData.postValue(it)
                GitHubRepoState.Success(it)
            }
    }

    fun getLiveData(): LiveData<List<GitHubRepoModel>> = liveData
}