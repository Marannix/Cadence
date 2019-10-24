package com.marannix.android.cadence.activity

import android.os.Bundle
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marannix.android.cadence.R
import com.marannix.android.cadence.adapter.GithubRepoAdapter
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GithubRepoViewModel
    private lateinit var liveData: LiveData<List<GitHubRepoModel>>
    private lateinit var githubRepoAdapter: GithubRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GithubRepoViewModel::class.java)
        init()
    }

    private fun init() {
        retrieveGithubRepos()
        initAdapter()
        updateUI()
    }

    private fun initAdapter() {
        githubRepoAdapter = GithubRepoAdapter()
        githubRepoRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        githubRepoRecyclerView.adapter = githubRepoAdapter
    }

    private fun retrieveGithubRepos() {
        viewModel.storeGithubRepos()
        liveData = viewModel.getListOfGithubRepo()
    }

    private fun updateUI() {
        liveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                githubRepoAdapter.setData(it)
            }
        })
    }
}
