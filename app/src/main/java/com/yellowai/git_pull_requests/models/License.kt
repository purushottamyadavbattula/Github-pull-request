package com.yellowai.git_pull_requests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class License(
    val key: String?,
    val name: String?,
    val node_id: String?,
    val spdx_id: String?,
    val url: String?
) : Parcelable