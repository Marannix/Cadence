package com.marannix.android.cadence.dagger.modules

import com.marannix.android.cadence.api.GithubRepoApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * This module exposes the API endpoint components
 */

const val BASE_URL = "http://api.github.com/"

@Module
class ApiModule {

    @Provides @Singleton fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides @Singleton fun provideGithubRepoApi(retrofit: Retrofit): GithubRepoApi {
        return retrofit.create(GithubRepoApi::class.java)
    }
}