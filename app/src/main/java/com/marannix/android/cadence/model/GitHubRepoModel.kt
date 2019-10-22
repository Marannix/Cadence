package com.marannix.android.cadence.model

import androidx.room.Entity

@Entity(tableName = "repos")
data class GitHubRepoModel(
    val name: String
)