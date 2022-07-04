package com.yellowai.git_pull_requests.ui.repos

import android.content.res.ColorStateList
import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yellowai.git_pull_requests.R
import com.yellowai.git_pull_requests.databinding.RepositoryTileBinding
import com.yellowai.git_pull_requests.models.Repo
import com.yellowai.git_pull_requests.ui.ActionListeners
import com.yellowai.git_pull_requests.utils.LanguageColorGenerator
import com.yellowai.git_pull_requests.utils.getDateAndTimeInMilSeconds

class RepoViewHolder(val binding: RepositoryTileBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(repo: Repo, actionListener: ActionListeners) {
        setRepoName(repo)
        setDescription(repo)
        setRepoLanguage(repo)
        setLastUpdatedTime(repo)
        setRepoPublicVisibility(repo)
        attachClickListener(repo, actionListener)
    }

    private fun attachClickListener(repo: Repo, actionListener: ActionListeners) {
        binding.root.setOnClickListener {
            actionListener.onClicked(repo)
        }
    }

    private fun setRepoPublicVisibility(repo: Repo) {
        repo.visibility?.let {
            binding.visibility.text = it
        }
    }

    private fun setLastUpdatedTime(repo: Repo) {
        repo.updated_at?.let {
            getDateAndTimeInMilSeconds(it)?.let { timestamp ->
                DateUtils.getRelativeDateTimeString(
                    binding.root.context,
                    timestamp,
                    DateUtils.DAY_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL
                )?.let { time ->
                    binding.updated.text = binding.root.context.getString(R.string.updated_on, time)
                }
            }
        }
    }

    private fun setRepoLanguage(repo: Repo) {
        repo.language?.let {
            binding.language.text = it
            binding.languageIcon.imageTintList =
                ColorStateList.valueOf(LanguageColorGenerator.generateColorForLanguage(it))
        }
    }

    private fun setDescription(repo: Repo) {
        repo.description?.let {
            binding.repoDescription.text = it
            binding.repoDescription.visibility = View.VISIBLE
        }
    }

    private fun setRepoName(repo: Repo) {
        repo.name?.let {
            binding.repoName.text = it
        }
    }
}