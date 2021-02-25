package com.assignment.jakewharton.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(
    @Expose val id: Long,
    @Expose val type: String,
    @Expose val actor: Actor,
    @Expose @SerializedName("created_at") val created: Date
)