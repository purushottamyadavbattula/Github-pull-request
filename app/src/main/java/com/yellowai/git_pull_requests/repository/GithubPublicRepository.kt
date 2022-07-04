package com.yellowai.git_pull_requests.repository

import com.yellowai.git_pull_requests.data.ApiService
import com.yellowai.git_pull_requests.models.Profile
import com.yellowai.git_pull_requests.models.PullRequest
import com.yellowai.git_pull_requests.models.Repo
import com.yellowai.git_pull_requests.utils.*
import retrofit2.Response

class GithubPublicRepository(val apiService: ApiService?) {

    suspend fun getProfile(): Profile? {
        if (apiService != null) {
            val response: Response<Profile> =
                apiService.getProfile(user = USER, authorization = TOKEN)
            if (isApiSuccessful(response)) {
                return response.body()
            }
        } else {
            throw Exception(API_SERVICE_NOT_INITIALISED)
        }
        return null
    }

    suspend fun getRepos(): List<Repo>? {
        if (apiService != null) {
            val response: Response<List<Repo>> =
                apiService.getAllRepositories(user = USER, authorization = TOKEN, PER_PAGE)
            if (isApiSuccessful(response)) {
                return response.body()
            }
        } else {
            throw Exception(API_SERVICE_NOT_INITIALISED)
        }
        return null
    }

    suspend fun getInitialData() {

    }

    suspend fun getClosedPullRequests(repoName: String): List<PullRequest>? {
        if (apiService != null) {
            val response: Response<List<PullRequest>> = apiService.getClosedPullRequests(
                user = USER,
                repoName = repoName,
                state = PULL_REQUEST_STATE,
                perPage = PER_PAGE,
                authorization = TOKEN
            )
            if (isApiSuccessful(response)) {
                return response.body()
            }
        } else {
            throw Exception(API_SERVICE_NOT_INITIALISED)
        }
        return null
    }

    private fun <T> isApiSuccessful(response: Response<T>): Boolean {
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception(API_FAILURE)
        }
    }
}