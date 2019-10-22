package com.marannix.android.cadence.data

import com.marannix.android.cadence.api.GithubRepoApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://api.github.com/"

interface GithubApi {

    private fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideGithubRepoApi(retrofit: Retrofit): GithubRepoApi {
        return retrofit.create(GithubRepoApi::class.java)
    }

    fun githubRepoApi(): GithubRepoApi {
        return provideGithubRepoApi(provideRetrofit())
    }

}