package com.marannix.android.cadence.viewmodel

import android.content.Context
import android.widget.Toast
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
    private val githubRepoUseCase: GithubRepoUseCase,
    private val context: Context
) : ViewModel() {

    var repos = MutableLiveData<List<GitHubRepoModel>>()
    private val disposables = CompositeDisposable()

    fun getListOfGithubRepo(): LiveData<List<GitHubRepoModel>> {
        return githubRepoUseCase.getStoredGithubRepos()
    }

    fun storeGithubRepos() {
        disposables.add(githubRepoUseCase.handleGitHubRepoState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleState(it)
            }
        )
    }

    private fun handleState(it: GitHubRepoState) {
        when (it) {
            is GitHubRepoState.Success -> {
                repos.value = it.gitHubRepoModel
            }
            is GitHubRepoState.Error -> {
                Toast.makeText(context, it.cause.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}