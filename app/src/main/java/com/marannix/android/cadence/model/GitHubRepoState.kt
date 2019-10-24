package com.marannix.android.cadence.model

sealed class GitHubRepoState {
    object Loading: GitHubRepoState()
    data class Success(val gitHubRepoModel: List<GitHubRepoModel>): GitHubRepoState()
    data class Error(val cause: Throwable) : GitHubRepoState()
}