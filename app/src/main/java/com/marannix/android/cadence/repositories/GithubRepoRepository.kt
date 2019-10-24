package com.marannix.android.cadence.repositories

import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    private val githubRepoDao: GithubRepoDao,
    private val githubRepoApi: GithubRepoApi
) {

    private fun getGithubReposFromApi(): Single<List<GitHubRepoModel>> {
        return githubRepoApi.getRepos()
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                storeGithubReposInDb(it)
            }
    }

    private fun storeGithubReposInDb(it: List<GitHubRepoModel>) {
        githubRepoDao.insertGithubRepos(it)
    }

    private fun getGithubReposFromDb(): Single<List<GitHubRepoModel>> {
        return githubRepoDao.getGithubRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Chaining request to database and network data source
     */
    fun getGithubRepos(): Flowable<List<GitHubRepoModel>> {
        return Single.concatArray(
            getGithubReposFromDb(),
            getGithubReposFromApi()
        )
    }
}