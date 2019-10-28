package com.marannix.android.cadence.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository
import com.marannix.android.cadence.utils.RxImmediateSchedulerRule
import com.marannix.android.cadence.utils.UnitTestUtils
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito

class GithubRepoUseCaseTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository by lazy { GithubRepoRepository(dao, api) }
    private val githubRepoUseCase by lazy { GithubRepoUseCase(repository) }

    private val api = Mockito.mock(GithubRepoApi::class.java)
    private val dao = Mockito.mock(GithubRepoDao::class.java)

    private val gson = GsonBuilder().create()
    private lateinit var githubrepoResponse: List<GitHubRepoModel>

    private lateinit var expectedSuccessState: GithubRepoRepository.GithubRepoDataState.Success
    private val expectedErrorState = GithubRepoRepository.GithubRepoDataState.Error("")

    private lateinit var state: GithubRepoRepository.GithubRepoDataState

    @Before
    fun setUp() {
        val response = UnitTestUtils.readJsonFile("githubrepo.json")
        githubrepoResponse = gson.fromJson(response, Array<GitHubRepoModel>::class.java).toList()
        expectedSuccessState = GithubRepoRepository.GithubRepoDataState.Success(githubrepoResponse)
    }

    @Test
    fun `when network succeed, emit data success state`() {
        Mockito.`when`(api.getRepos()).thenReturn(Single.just(githubrepoResponse))
        Mockito.`when`(dao.getGithubRepos()).thenReturn(Single.just(githubrepoResponse))

        githubRepoUseCase.getGithubRepoDataState().subscribe {
            state = it
        }
        Assert.assertEquals(state, expectedSuccessState)
    }

    @Test
    fun `when no network and data is stored in database, emit error data state`() {
        Mockito.`when`(api.getRepos()).thenReturn(Single.error(Throwable("")))
        Mockito.`when`(dao.getGithubRepos()).thenReturn(Single.just(githubrepoResponse))

        githubRepoUseCase.getGithubRepoDataState().subscribe {
            state = it
        }
        Assert.assertEquals(state, expectedErrorState)
    }

    @Test
    fun `when network fails and no data is stored, emit error data state`() {
        Mockito.`when`(api.getRepos()).thenReturn(Single.error(Throwable("")))
        Mockito.`when`(dao.getGithubRepos()).thenReturn(Single.just(emptyList()))

        githubRepoUseCase.getGithubRepoDataState().subscribe {
            state = it
        }
        Assert.assertEquals(state, expectedErrorState)
    }
}