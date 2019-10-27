package com.marannix.android.cadence.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marannix.android.cadence.usecase.GithubRepoUseCase
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository
import com.marannix.android.cadence.repositories.GithubRepoRepository.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(
    private val githubRepoUseCase: GithubRepoUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<GithubRepoViewState>()

    init {
        getGithubRepos()
    }

    fun getGithubRepos() {
        disposables.add(githubRepoUseCase.getGithubRepoDataState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { githubDataState ->
                return@map when (githubDataState) {
                    is GitHubRepoDataState.Success -> GithubRepoViewState.showGithubRepos(
                        githubDataState.gitHubRepoModel
                    )

                    is GitHubRepoDataState.Error ->
                        if (viewState.value is GithubRepoViewState.showGithubRepos) {
                            return@map viewState.value
                        } else {
                            return@map GithubRepoViewState.showError(githubDataState.cause.message)
                        }

                }
            }
            .subscribe { githubDataState ->
                viewState.value = githubDataState
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class GithubRepoViewState {
        object Loading : GithubRepoViewState()
        data class showGithubRepos(val gitHubRepoModel: List<GitHubRepoModel>) : GithubRepoViewState()
        data class showError(val error: String?) : GithubRepoViewState()
    }
}