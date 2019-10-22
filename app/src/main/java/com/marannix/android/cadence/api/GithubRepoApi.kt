package com.marannix.android.cadence.api

import com.marannix.android.cadence.model.GitHubRepoModel
import io.reactivex.Single
import retrofit2.http.GET

interface GithubRepoApi {

    @GET("orgs/square/repos")
    fun getRepos() : Single<List<GitHubRepoModel>>
}