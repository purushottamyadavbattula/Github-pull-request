package com.yellowai.git_pull_requests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Self(
    val href: String?
) : Parcelable