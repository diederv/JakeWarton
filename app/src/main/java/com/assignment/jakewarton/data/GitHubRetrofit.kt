package com.assignment.jakewarton.data

import android.app.usage.UsageEvents
import com.assignment.jakewarton.model.Event
import com.assignment.jakewarton.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubRetrofit {

    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>

    @GET("repos/{user}/{repo}/events")
    suspend fun getEvents(@Path("user") user: String, @Path("repo") repo: String): List<Event>
}