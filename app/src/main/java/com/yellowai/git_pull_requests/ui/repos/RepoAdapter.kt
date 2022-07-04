package com.yellowai.git_pull_requests.ui.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yellowai.di.AppModule
import com.yellowai.git_pull_requests.databinding.RepositoryTileBinding
import com.yellowai.git_pull_requests.models.Repo
import com.yellowai.git_pull_requests.ui.ActionListeners

class RepoAdapter(private val repos: List<Repo>, private val actionListener: ActionListeners) :
    ListAdapter<Repo, RepoViewHolder>(
        AsyncDifferConfig.Builder<Repo>(object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.node_id == newItem.node_id
            }
        })
            .setBackgroundThreadExecutor(AppModule.getAppExecutorReference()?.diskIO())
            .build()
    ) {
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val repositoryTileBinding: RepositoryTileBinding =
            RepositoryTileBinding.inflate(inflater!!, parent, false)

        return RepoViewHolder(repositoryTileBinding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        return holder.bind(repos[position], actionListener)
    }

    override fun getItem(position: Int): Repo {
        return repos[position]
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}