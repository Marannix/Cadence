package com.marannix.android.cadence.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.repositories.GithubRepoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) : ViewModel() {

    fun getDataFromApi() {
       githubRepoRepository.storeGithubRepos()
    }

    fun getLiveData() : MutableLiveData<String> {
        return githubRepoRepository.getLiveData()
    }
}