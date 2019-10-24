package com.marannix.android.cadence.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository
) : ViewModel() {

    fun getDataFromApi() {
       githubRepoRepository.storeGithubRepos()
    }

    fun getLiveData() : MutableLiveData<List<GitHubRepoModel>> {
        return githubRepoRepository.getLiveData()
    }

    fun getStoredGithubRepos(): LiveData<List<GitHubRepoModel>> {
        return githubRepoRepository.getGithubRepos()
    }
}