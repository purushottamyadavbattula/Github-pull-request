package com.yellowai.git_pull_requests.data

import com.yellowai.git_pull_requests.models.Profile
import com.yellowai.git_pull_requests.models.PullRequest
import com.yellowai.git_pull_requests.models.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/repos/{user}/{repoName}/pulls")
    suspend fun getClosedPullRequests(
        @Path("user") user: String,
        @Path("repoName") repoName: String,
        @Query("state") state: String,
        @Query("per_page") perPage: Int,
        @Header("Authorization") authorization: String
    ): Response<List<PullRequest>>

    @GET("/users/{user}")
    suspend fun getProfile(
        @Path("user") user: String,
        @Header("Authorization") authorization: String
    ): Response<Profile>

    @GET("/users/{user}/repos")
    suspend fun getAllRepositories(
        @Path("user") user: String,
        @Header("c") authorization: String,
        @Query("per_page") perPage: Int,
    ): Response<List<Repo>>
}