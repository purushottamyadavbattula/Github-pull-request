package com.yellowai.git_pull_requests.ui.pulls

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yellowai.di.AppModule
import com.yellowai.git_pull_requests.databinding.PullTileBinding
import com.yellowai.git_pull_requests.models.PullRequest

class PullRequestAdapter(private val pullReq: List<PullRequest>) :
    ListAdapter<PullRequest, PullViewHolder>(
        AsyncDifferConfig.Builder<PullRequest>(object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
                return oldItem.node_id == newItem.node_id
            }
        })
            .setBackgroundThreadExecutor(AppModule.getAppExecutorReference()?.diskIO())
            .build()
    ) {
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val pullTileBinding: PullTileBinding =
            PullTileBinding.inflate(inflater!!, parent, false)

        return PullViewHolder(pullTileBinding)
    }

    override fun onBindViewHolder(holder: PullViewHolder, position: Int) {
        return holder.bind(pullReq[position])
    }

    override fun getItem(position: Int): PullRequest {
        return pullReq[position]
    }

    override fun getItemCount(): Int {
        return pullReq.size
    }
}