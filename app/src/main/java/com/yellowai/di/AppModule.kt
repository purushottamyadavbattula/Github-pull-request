package com.yellowai.di

import com.google.gson.GsonBuilder
import com.yellowai.AppExecutors
import com.yellowai.git_pull_requests.data.ApiService
import com.yellowai.git_pull_requests.repository.GithubPublicRepository
import com.yellowai.git_pull_requests.utils.BASE_URL
import com.yellowai.git_pull_requests.utils.CurlLoggingInterceptor
import com.yellowai.git_pull_requests.utils.GsonSingleton
import com.yellowai.git_pull_requests.utils.SHOULD_ENABLE_DEBUGGING_MODE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppModule {
    companion object {
        private var apiService: ApiService? = null
        private var githubRepository: GithubPublicRepository? = null
        private var appExecutor: AppExecutors? = null

        fun getApiServiceReference(): ApiService? {
            if (apiService != null) return apiService
            else {
                val gsonDeserializer = GsonBuilder()
                    .registerTypeAdapter(Boolean::class.java, GsonSingleton.booleanTypeAdapter)
                    .registerTypeAdapter(
                        Boolean::class.javaPrimitiveType,
                        GsonSingleton.booleanTypeAdapter
                    )
                    .create()
                val retrofitBuilder = Retrofit.Builder()
                retrofitBuilder
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonDeserializer))

                if (SHOULD_ENABLE_DEBUGGING_MODE) {
                    val timeout = 2L

                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY

                    val httpClient = OkHttpClient.Builder()
                    httpClient.connectTimeout(timeout, TimeUnit.MINUTES)
                    httpClient.writeTimeout(timeout, TimeUnit.MINUTES)
                    httpClient.readTimeout(timeout, TimeUnit.MINUTES)
                    httpClient.addInterceptor(logging)
                    httpClient.addNetworkInterceptor(CurlLoggingInterceptor())

                    retrofitBuilder.client(httpClient.build())
                }

                apiService = retrofitBuilder
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService
        }

        fun getGithubRepositoryReference(): GithubPublicRepository? {
            if (githubRepository != null) return githubRepository
            else githubRepository = GithubPublicRepository(getApiServiceReference())
            return githubRepository
        }

        fun getAppExecutorReference(): AppExecutors? {
            if (appExecutor != null) return appExecutor
            else appExecutor = AppExecutors()
            return appExecutor
        }
    }

}