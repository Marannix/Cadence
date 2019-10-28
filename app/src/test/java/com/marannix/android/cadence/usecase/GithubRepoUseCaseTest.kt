package com.marannix.android.cadence.usecase

import com.marannix.android.cadence.repositories.GithubRepoRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class GithubRepoUseCaseTest {

    private val githubRepoRepository = Mockito.mock(GithubRepoRepository::class.java)

    private lateinit var githubRepoUseCase: GithubRepoUseCase

    @Before
    fun setUp() {
        githubRepoUseCase = GithubRepoUseCase(githubRepoRepository)
    }

    @Test
    fun getGithubRepoDataState() {
        githubRepoUseCase
    }
}