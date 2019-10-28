package com.marannix.android.cadence.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.GsonBuilder
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.repositories.GithubRepoRepository
import com.marannix.android.cadence.usecase.GithubRepoUseCase
import com.marannix.android.cadence.utils.RxImmediateSchedulerRule
import com.marannix.android.cadence.utils.UnitTestUtils
import io.reactivex.Single
import junit.framework.Assert.assertEquals

import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GithubRepoViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val gson = GsonBuilder().create()
    private lateinit var githubrepoResponse: List<GitHubRepoModel>

    private val api = Mockito.mock(GithubRepoApi::class.java)
    private val dao = Mockito.mock(GithubRepoDao::class.java)

    private val repository by lazy { GithubRepoRepository(dao, api) }
    private val viewModel by lazy { GithubRepoViewModel(GithubRepoUseCase(repository)) }

    private val expectedLoadingState = GithubRepoViewModel.GithubRepoViewState.Loading
    private lateinit var expectedSuccessState: GithubRepoViewModel.GithubRepoViewState.ShowGithubRepos
    private val expectedErrorState = GithubRepoViewModel.GithubRepoViewState.ShowError("")

    /**
     *  The idea here is to observe the view state to capture the state when it changes between states
     *  i.e Loading State -> Success State
     *  ( This observer doesn't work :((()
     */
    @Mock
    lateinit var observerState: Observer<GithubRepoViewModel.GithubRepoViewState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val response = UnitTestUtils.readJsonFile("githubrepo.json")
        githubrepoResponse = gson.fromJson(response, Array<GitHubRepoModel>::class.java).toList()
        expectedSuccessState = GithubRepoViewModel.GithubRepoViewState.ShowGithubRepos(githubrepoResponse)
    }

    @Test
    fun `when network fails and no data stored emit error view state`() {
        Mockito.`when`(api.getRepos()).thenReturn(Single.error(Throwable("")))
        Mockito.`when`(dao.getGithubRepos()).thenReturn(Single.just(emptyList()))
        viewModel.viewState.observeForever { observerState }
        assertEquals(viewModel.viewState.value, expectedErrorState)
    }

    @Test
    fun `when network succeed emit success state`() {
        Mockito.`when`(api.getRepos()).thenReturn(Single.just(githubrepoResponse))
        Mockito.`when`(dao.getGithubRepos()).thenReturn(Single.just(githubrepoResponse))
        viewModel.viewState.observeForever { observerState }
        assertEquals(viewModel.viewState.value, expectedSuccessState)
    }

    @Test
    fun `when network fails and data exists in database emit success state`() {
        Mockito.`when`(api.getRepos()).thenReturn(Single.error(Throwable("")))
        Mockito.`when`(dao.getGithubRepos()).thenReturn(Single.just(githubrepoResponse))
        viewModel.viewState.observeForever { observerState }
        assertEquals(viewModel.viewState.value, expectedSuccessState)
    }
}