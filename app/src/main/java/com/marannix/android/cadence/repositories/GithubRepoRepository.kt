package com.marannix.android.cadence.repositories

import android.content.Context
import android.widget.Toast
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
    private val context: Context,
    private val githubRepoDao: GithubRepoDao,
    private val githubRepoApi: GithubRepoApi
) {

    private var liveData: MutableLiveData<List<GitHubRepoModel>> = MutableLiveData()
    private val disposables = CompositeDisposable()

    fun storeGithubRepos() {
        val disposable = githubRepoApi.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos ->
                    githubRepoDao.insertGithubRepos(repos)
                    updateLiveData(repos)
                }
                ,
                {
                    // TODO: Think of a suitable error
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                })

        disposables.add(disposable)
    }

    private fun updateLiveData(repos: List<GitHubRepoModel>) {
        liveData.value = repos
    }

    fun getGithubRepos(): LiveData<List<GitHubRepoModel>> {
        return githubRepoDao.getAllGithubRepos()
    }

    fun getLiveData(): MutableLiveData<List<GitHubRepoModel>> {
        return liveData
    }
}