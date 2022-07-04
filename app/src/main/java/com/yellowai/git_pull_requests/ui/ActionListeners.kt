package com.yellowai.git_pull_requests.ui

import com.yellowai.git_pull_requests.models.Repo

interface ActionListeners {
    fun onClicked(repo: Repo)
}