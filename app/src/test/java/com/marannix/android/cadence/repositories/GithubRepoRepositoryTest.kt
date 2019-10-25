package com.marannix.android.cadence.repositories

import com.marannix.android.cadence.api.GithubRepoApi
import org.junit.Before
import org.junit.Test
import com.google.gson.GsonBuilder
import com.marannix.android.cadence.data.GithubRepoDao
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.utils.UnitTestUtils.Companion.readJsonFile
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.*
import retrofit2.HttpException

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
        `when`(dao.getGithubRepos()).thenReturn(Single.just(emptyList()))

        repository.getGithubRepos().subscribe { result = it }

        assertThat(result.size).isEqualTo(githubrepoResponse.size)
        assertThat(result.first()).isEqualTo(githubrepoResponse.first())
        assertThat(result.last()).isEqualTo(githubrepoResponse.last())

        verify(api).getRepos()
    }

    @Test
    fun `when network fails, parse correctly the response from cache`() {
        // TODO: Test passes but returns exception due to throwable >.>
        //  *The exception was not handled due to missing onError handler in the subscribe() method call.*
        // The idea is to make the api request fail and retrieve data from the cache response
        // Tried using Single.just(emptyList()) however the response becomes empty, hmmm...

        `when`(api.getRepos()).thenReturn(Single.error(Throwable("")))
        `when`(dao.getGithubRepos()).thenReturn(Single.just(githubrepoResponse))

        repository.getGithubRepos().subscribe { result = it }

        assertThat(result.size).isEqualTo(githubrepoResponse.size)
        assertThat(result.first()).isEqualTo(githubrepoResponse.first())
        assertThat(result.last()).isEqualTo(githubrepoResponse.last())

        verify(dao).getGithubRepos()
    }
}








