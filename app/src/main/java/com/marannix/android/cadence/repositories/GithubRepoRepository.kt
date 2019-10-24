package com.marannix.android.cadence.repositories

import androidx.lifecycle.LiveData
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    private val githubRepoDao: GithubRepoDao,
    private val githubRepoApi: GithubRepoApi
) {

    fun getGithubRepos(): Single<List<GitHubRepoModel>> {
        return githubRepoApi.getRepos()
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                githubRepoDao.insertGithubRepos(it)
            }
    }

    fun getAllGithubRepos(): LiveData<List<GitHubRepoModel>> {
        return githubRepoDao.getAllGithubRepos()
    }
}