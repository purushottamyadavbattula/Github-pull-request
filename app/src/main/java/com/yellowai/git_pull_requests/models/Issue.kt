package com.yellowai.git_pull_requests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Issue(
    val href: String?
) : Parcelable