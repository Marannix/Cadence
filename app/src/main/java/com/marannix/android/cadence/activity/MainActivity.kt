package com.marannix.android.cadence.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.marannix.android.cadence.R
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var viewModel: GithubRepoViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var liveData: MutableLiveData<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubRepoViewModel::class.java)
        viewModel.getDataFromApi()

        liveData = viewModel.getLiveData()

        liveData.observe(this, Observer {
            Log.d("LiveData2", liveData.value.toString())
        })
    }
}
