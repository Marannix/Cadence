package com.marannix.android.cadence.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marannix.android.cadence.usecase.GithubRepoUseCase
import com.marannix.android.cadence.model.GitHubRepoState
import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(
    private val githubRepoUseCase: GithubRepoUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    var state = MutableLiveData<GitHubRepoState>()

    fun storeGithubRepos()  {
        disposables.add(githubRepoUseCase.handleGitHubRepoState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { githubRepoState ->
                handleState(githubRepoState)
            }
        )
    }

    fun getLiveData() : LiveData<List<GitHubRepoModel>> {
        return githubRepoUseCase.getLiveData()
    }

    private fun handleState(githubRepoState: GitHubRepoState) {
        state.postValue(githubRepoState)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}