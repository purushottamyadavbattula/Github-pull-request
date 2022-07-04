package com.yellowai.git_pull_requests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PullRequest(
    val _links: Links?,
    val active_lock_reason: @RawValue Any?,
    val assignee: @RawValue Any?,
    val assignees: List<@RawValue Any?>?,
    val author_association: String?,
    val auto_merge: @RawValue Any?,
    val base: Base?,
    val body: String?,
    val closed_at: String?,
    val comments_url: String?,
    val commits_url: String?,
    val created_at: String?,
    val diff_url: String?,
    val draft: Boolean?,
    val head: Head?,
    val html_url: String?,
    val id: Int?,
    val issue_url: String?,
    val labels: List<@RawValue Any?>?,
    val locked: Boolean?,
    val merge_commit_sha: String?,
    val merged_at: String?,
    val milestone: @RawValue Any?,
    val node_id: String?,
    val number: Int?,
    val patch_url: String?,
    val requested_reviewers: List<@RawValue Any?>?,
    val requested_teams: List<@RawValue Any?>?,
    val review_comment_url: String?,
    val review_comments_url: String?,
    val state: String?,
    val statuses_url: String?,
    val title: String?,
    val updated_at: String?,
    val url: String?,
    val user: User?
) : Parcelable