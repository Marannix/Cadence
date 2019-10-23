package com.marannix.android.cadence.repositories

import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel

class GithubRepoRepository(val githubRepoDao: GithubRepoDao) {

    fun storeGithubRepos(repos: List<GitHubRepoModel>) {
        githubRepoDao.insertGithubRepos(repos)
    }

    fun getGithubRepos(): List<GitHubRepoModel> {
        return githubRepoDao.getAllGithubRepos()
    }
}