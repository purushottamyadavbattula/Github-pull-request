package com.yellowai.git_pull_requests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Base(
    val label: String?,
    val ref: String?,
    val repo: Repo?,
    val sha: String?,
    val user: User?
) : Parcelable