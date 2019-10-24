package com.marannix.android.cadence.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.repositories.GithubRepoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(
    private val githubRepoApi: GithubRepoApi,
    private val githubRepoRepository: GithubRepoRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun getDataFromApi() {
        val disposable = githubRepoApi.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { model ->
                    // TODO: Turn this to live data
                    Log.d("Yes", model[2].name)
                    githubRepoRepository.storeGithubRepos(model)
                }
                ,
                {
                    Log.d("No", it.message!!)
                })

        Log.d("LOL", githubRepoRepository.getGithubRepos().size.toString())
        disposables.add(disposable)
    }
}