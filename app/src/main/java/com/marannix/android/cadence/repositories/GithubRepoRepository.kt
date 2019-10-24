package com.marannix.android.cadence.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    val githubRepoDao: GithubRepoDao,
    val githubRepoApi: GithubRepoApi
) {

    private var liveData: MutableLiveData<String> = MutableLiveData()

    private val disposables = CompositeDisposable()

    private fun getGithubRepos(): List<GitHubRepoModel> {
        return githubRepoDao.getAllGithubRepos()
    }

    fun storeGithubRepos() {
        val disposable = githubRepoApi.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { model ->
                    Log.d("Yes", model[2].name)
                    githubRepoDao.insertGithubRepos(model)
                    liveData.value = model[1].name
                }
                ,
                {
                    Log.d("No", it.message!!)
                })

        Log.d("LOL", getGithubRepos().size.toString())
        disposables.add(disposable)
    }

    fun getLiveData(): MutableLiveData<String> {
        return liveData
    }
}