package com.marannix.android.cadence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class GitHubRepoModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)