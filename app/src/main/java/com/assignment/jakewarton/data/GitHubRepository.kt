package com.assignment.jakewarton.data

import com.assignment.jakewarton.model.Event
import com.assignment.jakewarton.model.Repo

interface GitHubRepository {

    suspend fun getRepos(user: String): List<Repo>

    suspend fun getMultipleRepos(users: List<String>): List<Repo>

    suspend fun getRepoEvents(repo: Repo): List<Event>

}