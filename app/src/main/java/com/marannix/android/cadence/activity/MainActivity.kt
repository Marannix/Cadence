package com.marannix.android.cadence.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.marannix.android.cadence.R
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GithubRepoViewModel
    private lateinit var liveData: MutableLiveData<List<GitHubRepoModel>>
    private lateinit var liveDataRepo: LiveData<List<GitHubRepoModel>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubRepoViewModel::class.java)
        viewModel.getDataFromApi()

        liveData = viewModel.getLiveData()
        liveDataRepo = viewModel.getStoredGithubRepos()

        liveData.observe(this, Observer {
            Log.d("LiveData2", liveData.value.toString())
        })

        liveDataRepo.observe(this, Observer {
            Log.d("Database", liveDataRepo.value.toString())
        })
    }
}
