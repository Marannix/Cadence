package com.marannix.android.cadence.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marannix.android.cadence.usecase.GithubRepoUseCase
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(
    private val githubRepoUseCase: GithubRepoUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    val viewState = MutableLiveData<GithubRepoViewState>()
    val actionState = MutableLiveData<GithubRepoActionState>()

    init {
        getGithubRepos()
    }

    fun getGithubRepos() {
        disposables.add(
            githubRepoUseCase.getGithubRepoDataState()
                .observeOn(AndroidSchedulers.mainThread())
                .map { gitHubDataState ->
                    return@map when (gitHubDataState) {
                        is GithubRepoDataState.Success -> {
                            GithubRepoViewState.ShowGithubRepos(gitHubDataState.gitHubRepoModel)
                        }
                        is GithubRepoDataState.Error -> {
                            val errorMessage = gitHubDataState.errorMessage
                            actionState.value = GithubRepoActionState.ShowSecondaryError(errorMessage)
                            if (viewState.value is GithubRepoViewState.ShowGithubRepos) {
                                viewState.value
                            } else {
                                GithubRepoViewState.ShowError(errorMessage)
                            }

                        }
                    }
                }
                .doOnSubscribe { viewState.value = GithubRepoViewState.Loading }
                .subscribe { viewState ->
                    this.viewState.value = viewState
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class GithubRepoViewState {
        object Loading : GithubRepoViewState()
        data class ShowGithubRepos(val gitHubRepoModel: List<GitHubRepoModel>) : GithubRepoViewState()
        data class ShowError(val errorMessage: String?) : GithubRepoViewState()
    }

    sealed class GithubRepoActionState {
        data class ShowSecondaryError(val errorMessage: String?) : GithubRepoActionState()
    }
}