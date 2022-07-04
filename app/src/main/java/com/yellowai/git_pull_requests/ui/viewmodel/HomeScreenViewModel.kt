package com.yellowai.git_pull_requests.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yellowai.di.AppModule
import com.yellowai.git_pull_requests.models.Profile
import com.yellowai.git_pull_requests.models.Repo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    val githubRepository = AppModule.getGithubRepositoryReference()
    private val _spinner = MutableLiveData<Boolean>(false)
    private val _snackbar = MutableLiveData<String?>()

    val spinner: LiveData<Boolean>
        get() = _spinner

    val profile = MutableLiveData<Profile?>()
    val repos = MutableLiveData<List<Repo>?>()

    fun getProfile() {
        githubRepository?.let {
            launchRequestWithOutSpinner {
                profile.value = it.getProfile()
            }
        }
    }

    fun getRepos() {
        githubRepository?.let {
            launchRequest {
                repos.value = it.getRepos()
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