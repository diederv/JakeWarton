package com.assignment.jakewharton.data

import com.assignment.jakewharton.model.Event
import com.assignment.jakewharton.model.Repo

interface GitHubRepository {

    suspend fun getRepos(user: String): List<Repo>

    suspend fun getMultipleRepos(users: List<String>): List<Repo>

    suspend fun getRepoEvents(repo: Repo): List<Event>

}