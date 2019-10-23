package com.marannix.android.cadence.activity

import android.os.Bundle
import android.util.Log
import com.marannix.android.cadence.R
import com.marannix.android.cadence.api.GithubRepoApi
import com.marannix.android.cadence.repositories.GithubRepoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var githubRepoApi: GithubRepoApi

    @Inject
    lateinit var githubRepoRepository: GithubRepoRepository

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = githubRepoApi.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {model ->

                    // TODO: Turn this to live data
                    Log.d("Yes", model[2].name)
                    githubRepoRepository.storeGithubRepos(model)
                }
                ,
                {
                    Log.d("No",it.message!!)
                })

        Log.d("LOL", githubRepoRepository.getGithubRepos().toString())
        disposables.add(disposable)

    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }
}
