package com.marannix.android.cadence.repositories

import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.Flowable
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
    fun getGithubRepos(): Flowable<List<GitHubRepoModel>> {
        return Single.concatArrayEager(
            getGithubReposFromDb(),
            getGithubReposFromApi()
        )
    }

    private fun getGithubReposFromApi(): Single<List<GitHubRepoModel>> {
        return githubRepoApi.getRepos()
            .doOnSuccess {modelList ->
                storeGithubReposInDb(modelList)
            }
    }

    private fun storeGithubReposInDb(list: List<GitHubRepoModel>) {
        githubRepoDao.insertGithubRepos(list)
    }

    private fun getGithubReposFromDb(): Single<List<GitHubRepoModel>> {
        return githubRepoDao.getGithubRepos()

//            .subscribeOn(Schedulers.io())
    }

    sealed class GitHubRepoDataState {
        data class Success(val gitHubRepoModel: List<GitHubRepoModel>) : GitHubRepoDataState()
        data class Error(val cause: Throwable) : GitHubRepoDataState()
    }
}