package com.marannix.android.cadence.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.marannix.android.cadence.R
import com.marannix.android.cadence.adapter.GithubRepoAdapter
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel
import com.marannix.android.cadence.viewmodel.GithubRepoViewModel.*
import kotlinx.android.synthetic.main.fragment_github_repo.*
import kotlinx.android.synthetic.main.github_repo_loading_state.*
import javax.inject.Inject

class GithubRepoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GithubRepoViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(GithubRepoViewModel::class.java)
    }
    private val githubRepoAdapter: GithubRepoAdapter by lazy { GithubRepoAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_github_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeToViewState()
    }

    private fun initRecyclerView() {
        githubRepoRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        githubRepoRecyclerView.adapter = githubRepoAdapter
    }

    private fun subscribeToViewState() {
        viewModel.viewState.observe(this, Observer { viewstate ->
            when (viewstate) {
                GithubRepoViewState.Loading -> {
                    displayLoading()
                }
                is GithubRepoViewState.showGithubRepos -> {
                    displaySuccess(viewstate)
                }
                is GithubRepoViewState.showError -> {
                    displayError(viewstate.error)
                }
            }
        })
    }

    private fun displayLoading() {
        loadingAnimation.visibility = View.VISIBLE
        errorAnimation.visibility = View.GONE
        reloadButton.visibility = View.GONE
    }

    private fun displaySuccess(it: GithubRepoViewState.showGithubRepos) {
        loadingAnimation.visibility = View.GONE
        errorAnimation.visibility = View.GONE
        reloadButton.visibility = View.GONE

        githubRepoAdapter.setData(it.gitHubRepoModel)
    }

    private fun displayError(error: String?) {
        loadingAnimation.visibility = View.GONE
        errorAnimation.visibility = View.VISIBLE
        reloadButton.visibility = View.VISIBLE

        // TODO Show error message :3
        /**
         * The idea is to fetch the github repos again when it fails
         * The button will only be present when an error occurs and no data has been stored
         * i.e launching the application the first time without internet connection
         */

        reloadButton.setOnClickListener {
            viewModel.getGithubRepos()
        }
    }
}
