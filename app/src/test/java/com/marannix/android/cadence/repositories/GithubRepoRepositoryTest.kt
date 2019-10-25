package com.marannix.android.cadence.repositories

import androidx.lifecycle.MutableLiveData
import com.marannix.android.cadence.api.GithubRepoApi
import org.junit.Before
import org.junit.Test
import com.google.gson.GsonBuilder
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.utils.UnitTestUtils.Companion.readJsonFile
import io.reactivex.FlowableSubscriber
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito
import org.mockito.Mockito.*


class GithubRepoRepositoryTest {

    private val api = mock(GithubRepoApi::class.java)
    private val dao = mock(GithubRepoDao::class.java)

    private val gson = GsonBuilder().create()

    private lateinit var githubrepoResponse: List<GitHubRepoModel>
    private lateinit var repository: GithubRepoRepository

    var result = emptyList<GitHubRepoModel>()

    @Before
    fun setUp() {
        val response = readJsonFile("githubrepo.json")
        githubrepoResponse = gson.fromJson(response, Array<GitHubRepoModel>::class.java).toList()
        repository = GithubRepoRepository(dao, api)
    }

    @Test
    fun `when calling the network, parse correctly the response`() {
        `when`(api.getRepos()).thenReturn(Single.just(githubrepoResponse))
        `when`(dao.getGithubRepos()).thenReturn(Single.just(githubrepoResponse))

        repository.getGithubRepos().subscribe { result = it }

        assertThat(result.size).isEqualTo(githubrepoResponse.size)
        assertThat(result.first()).isEqualTo(githubrepoResponse.first())
        assertThat(result.last()).isEqualTo(githubrepoResponse.last())

        verify(api).getRepos()
    }
}








