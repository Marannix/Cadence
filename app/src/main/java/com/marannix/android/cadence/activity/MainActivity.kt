package com.marannix.android.cadence.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.marannix.android.cadence.R
import com.marannix.android.cadence.data.GithubApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val githubApi = object : GithubApi {}
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = githubApi.githubRepoApi().getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("Yes", it[2].name)
                }
                ,
                {
                    Log.d("No",it.message!!)
                })

        disposables.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }
}
