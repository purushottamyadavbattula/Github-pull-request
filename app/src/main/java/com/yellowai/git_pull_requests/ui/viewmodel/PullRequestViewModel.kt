package com.yellowai.git_pull_requests.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yellowai.di.AppModule
import com.yellowai.git_pull_requests.models.PullRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PullRequestViewModel : ViewModel() {
    val githubRepository = AppModule.getGithubRepositoryReference()
    private val _spinner = MutableLiveData<Boolean>(false)
    private val _snackbar = MutableLiveData<String?>()

    val spinner: LiveData<Boolean>
        get() = _spinner

    val pullRequests = MutableLiveData<List<PullRequest>?>()

    fun getClosedPullRequests(repoName: String) {
        githubRepository?.let {
            launchRequest {
                pullRequests.value = it.getClosedPullRequests(repoName)
            }
        }
    }

    private fun launchRequest(callApi: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                callApi()
            } catch (error: Throwable) {
                _snackbar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }

    private fun launchRequestWithOutSpinner(callApi: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                callApi()
            } catch (error: Throwable) {
                _snackbar.value = error.message
            }
        }
    }
}