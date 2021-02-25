package com.assignment.jakewharton.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    @Expose val id: Int,
    @Expose val login: String,
    @Expose val url: String
): Parcelable