package com.assignment.jakewarton.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(
    @Expose val id: Int,
    @Expose val type: String,
    @Expose val actor: Actor,
    @Expose @SerializedName("created_at") val created: Date
)