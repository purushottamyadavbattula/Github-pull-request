package com.yellowai.git_pull_requests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    val comments: Comments?,
    val commits: Commits?,
    val html: Html?,
    val issue: Issue?,
    val review_comment: ReviewComment?,
    val review_comments: ReviewComments?,
    val self: Self?,
    val statuses: Statuses?
) : Parcelable