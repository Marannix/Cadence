package com.marannix.android.cadence.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marannix.android.cadence.R
import com.marannix.android.cadence.adapter.GithubRepoAdapter
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.model.GitHubRepoState
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.github_repo_loading_state.*
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

    private fun retrieveGithubRepos() {
        viewModel.storeGithubRepos()
        liveData = viewModel.getLiveData()
    }

    private fun initAdapter() {
        githubRepoAdapter = GithubRepoAdapter()
        githubRepoRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        githubRepoRecyclerView.adapter = githubRepoAdapter
    }

    private fun updateUI() {
        viewModel.state.observe(this, Observer {
            when (it) {
                GitHubRepoState.Loading -> {
                    displayLoading()
                }
                is GitHubRepoState.Success -> {
                    displaySuccess()
                    liveData.observe(this, Observer { model ->
                        githubRepoAdapter.setData(model)
                    })
                }
                is GitHubRepoState.Error -> {
                    liveData.observe(this, Observer { model ->
                        when {
                            !model.isNullOrEmpty() -> {
                                displaySuccess()
                                githubRepoAdapter.setData(model)
                            }
                            else -> {
                                 displayError()
                            }
                        }
                    })
                }
            }
        })
    }

    private fun displaySuccess() {
        loadingAnimation.visibility = View.GONE
        errorAnimation.visibility = View.GONE
    }

    private fun displayLoading() {
        loadingAnimation.visibility = View.VISIBLE
        errorAnimation.visibility = View.GONE
    }

    private fun displayError() {
        loadingAnimation.visibility = View.GONE
        errorAnimation.visibility = View.VISIBLE
    }
}