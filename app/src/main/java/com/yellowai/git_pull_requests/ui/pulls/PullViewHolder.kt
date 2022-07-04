package com.yellowai.git_pull_requests.ui.pulls

import android.text.format.DateUtils
import androidx.recyclerview.widget.RecyclerView
import com.yellowai.git_pull_requests.R
import com.yellowai.git_pull_requests.databinding.PullTileBinding
import com.yellowai.git_pull_requests.models.PullRequest
import com.yellowai.git_pull_requests.utils.getDateAndTimeInMilSeconds

class PullViewHolder(val binding: PullTileBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pullreq: PullRequest) {
        setTitle(pullreq)
        setPullRequestCount(pullreq)
        setUpdatedUserName(pullreq)
        setMergedTime(pullreq)
    }

    private fun setMergedTime(pullreq: PullRequest) {
        pullreq.merged_at?.let {
            getDateAndTimeInMilSeconds(it)?.let { timestamp ->
                DateUtils.getRelativeDateTimeString(
                    binding.root.context,
                    timestamp,
                    DateUtils.DAY_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL
                )?.let { time ->
                    binding.mergedOn.text = binding.root.context.getString(R.string.merged_at, time)
                }
            }
        }
    }

    private fun setUpdatedUserName(pullreq: PullRequest) {
        pullreq.user?.login?.let {
            binding.updated.text = binding.root.context.getString(R.string.by, it)
        }
    }

    private fun setPullRequestCount(pullreq: PullRequest) {
        pullreq.number?.let {
            binding.number.text = binding.root.context.getString(R.string.hash, it.toString())
        }
    }

    private fun setTitle(pullreq: PullRequest) {
        pullreq.title?.let {
            binding.pullTitle.text = it
        }
    }
}