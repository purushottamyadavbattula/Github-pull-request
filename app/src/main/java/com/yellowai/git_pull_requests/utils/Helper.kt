package com.yellowai.git_pull_requests.utils

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yellowai.git_pull_requests.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://api.github.com"
const val REPO = "repo"
const val SHOULD_ENABLE_DEBUGGING_MODE = true
// TODO insert token here
// Removed token for security concerns
const val TOKEN = "token <Token here>"
const val USER = "yellowmessenger"
const val PULL_REQUEST_STATE = "closed"
const val PER_PAGE = 100

const val API_SERVICE_NOT_INITIALISED = "Api service not initialised yet"
const val API_FAILURE = "Api failure"
const val ERROR_OCCURRED = "Error occurred"

internal fun showMessage(view: View, message: String = ERROR_OCCURRED) {
    Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_LONG
    )
        .setAction("Ok") { }
        .show()

}

internal fun loadImage(context: Context, url: String, view: ImageView) {
    Glide.with(context).load(url)
        .placeholder(R.drawable.ic_image)
        .error(R.drawable.broken_image)
        .into(view)
}

internal fun getDateAndTimeInMilSeconds(value: String?): Long? {
    if (value.isNullOrEmpty())
        return null

    if (TextUtils.isDigitsOnly(value)) {
        return value!!.toLong()
    }
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date? = dateFormat.parse(value!!)
    return date?.time
}

// This is a higher order function for changing tint of image view
fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}