package com.assignment.jakewharton.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    @Expose val id: Long,
    @Expose val name: String,
    @Expose @SerializedName("full_name") val fullName: String,
    @Expose val owner: Owner,
    @Expose @SerializedName("html_url") val htmlUrl: String
): Parcelable