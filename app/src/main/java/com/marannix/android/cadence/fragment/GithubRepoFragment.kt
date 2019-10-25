package com.marannix.android.cadence.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.marannix.android.cadence.R
import com.marannix.android.cadence.adapter.GithubRepoAdapter
import com.marannix.android.cadence.model.GitHubRepoModel
import com.marannix.android.cadence.model.GitHubRepoState
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import kotlinx.android.synthetic.main.fragment_github_repo.*
import kotlinx.android.synthetic.main.github_repo_loading_state.*
import javax.inject.Inject

class GithubRepoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GithubRepoViewModel
    private lateinit var liveData: LiveData<List<GitHubRepoModel>>
    private lateinit var githubRepoAdapter: GithubRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GithubRepoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_github_repo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        githubRepoRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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
                                setReloadButton()
                            }
                        }
                    })
                }
            }
        })
    }

    private fun displayLoading() {
        loadingAnimation.visibility = View.VISIBLE
        errorAnimation.visibility = View.GONE
        reloadButton.visibility = View.GONE
    }

    private fun displaySuccess() {
        loadingAnimation.visibility = View.GONE
        errorAnimation.visibility = View.GONE
        reloadButton.visibility = View.GONE
    }

    private fun displayError() {
        loadingAnimation.visibility = View.GONE
        errorAnimation.visibility = View.VISIBLE
        reloadButton.visibility = View.VISIBLE
    }

    private fun setReloadButton() {

    }

}
