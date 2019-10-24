package com.marannix.android.cadence.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.marannix.android.cadence.R
import com.marannix.android.cadence.model.GitHubRepoModel
import kotlinx.android.synthetic.main.github_repo_items.view.*

class GithubRepoAdapter : RecyclerView.Adapter<GithubRepoAdapter.ViewHolder>() {

    private var data : List<GitHubRepoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.github_repo_items, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            holder.bind(data[position])
        }
    }

    fun setData(gitHubRepoModel: List<GitHubRepoModel>) {
        data = gitHubRepoModel
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repos: GitHubRepoModel) {
            itemView.githubRepoName.text = repos.name
        }
    }
}