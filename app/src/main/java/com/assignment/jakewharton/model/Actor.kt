package com.assignment.jakewharton.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Actor(
    @Expose val id: Long,
    @Expose val url: String,
    @Expose val type: String,
    @Expose @SerializedName("display_login") val displayLogin: String
)