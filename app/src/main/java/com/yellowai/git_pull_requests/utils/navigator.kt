package com.yellowai.git_pull_requests.utils

import androidx.fragment.app.FragmentManager
import com.yellowai.git_pull_requests.R
import com.yellowai.git_pull_requests.models.Repo
import com.yellowai.git_pull_requests.ui.view.HomeScreen
import com.yellowai.git_pull_requests.ui.view.PullRequestsFragment

fun navigateToHomeScreen(
    fragmentManager: FragmentManager,
    tag: String
) {
    fragmentManager
        .beginTransaction()
        .replace(
            R.id.container,
            HomeScreen.newInstance(),
            tag
        )
        .addToBackStack(tag)
        .commit()
}

fun navigateToPullRequestsScreen(
    fragmentManager: FragmentManager,
    tag: String,
    repo: Repo
) {
    fragmentManager
        .beginTransaction()
        .replace(
            R.id.container,
            PullRequestsFragment.newInstance(repo),
            tag
        )
        .addToBackStack(tag)
        .commit()
}