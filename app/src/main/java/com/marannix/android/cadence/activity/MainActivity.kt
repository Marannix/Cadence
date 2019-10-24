package com.marannix.android.cadence.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.marannix.android.cadence.R
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var viewModel: GithubRepoViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubRepoViewModel::class.java)
        viewModel.getDataFromApi()
    }
}
