package com.marannix.android.cadence.repositories

import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    private val githubRepoDao: GithubRepoDao,
    private val githubRepoApi: GithubRepoApi
) {

    /**
     * Chaining request to database and network data source
     */
    fun getGithubRepos(): Observable<List<GitHubRepoModel>> {
        return Observable.concatArray(
            getGithubReposFromDb(),
            getGithubReposFromApi().toObservable()
        )
    }

    private fun getGithubReposFromApi(): Single<List<GitHubRepoModel>> {
        return githubRepoApi.getRepos()
            .doOnSuccess { list ->
                storeGithubReposInDb(list)
            }
            .subscribeOn(Schedulers.io())
    }

    private fun storeGithubReposInDb(list: List<GitHubRepoModel>) {
        githubRepoDao.insertGithubRepos(list)
    }

    private fun getGithubReposFromDb(): Observable<List<GitHubRepoModel>> {
        return githubRepoDao.getGithubRepos().toObservable()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    Observable.empty()
                } else {
                    Observable.just(list)
                }
            }
    }

    sealed class GithubRepoDataState {
        data class Success(val gitHubRepoModel: List<GitHubRepoModel>) : GithubRepoDataState()
        data class Error(val cause: Throwable) : GithubRepoDataState()
    }
}