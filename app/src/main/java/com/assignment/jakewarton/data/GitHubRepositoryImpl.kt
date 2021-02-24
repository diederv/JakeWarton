package com.assignment.jakewarton.data

import com.assignment.jakewarton.model.Event
import com.assignment.jakewarton.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip

open class GitHubRepositoryImpl constructor(
    val gitHubRetrofit: GitHubRetrofit
): GitHubRepository {

    override suspend fun getRepos(user: String): List<Repo> {
        return gitHubRetrofit.listRepos(user)
    }

    override suspend fun getMultipleRepos(users: List<String>): List<Repo> {
        if (users.size == 0) return listOf()
        if (users.size == 1) return getRepos(users.first())
        var zip : Flow<List<Repo>>? = null
        users.forEachIndexed() {  index, user ->
            val flow = flowOf(gitHubRetrofit.listRepos(user))
            when (index) {
                0 -> zip = flow
                else -> zip = zip!!.zip(flow) { a, b ->
                    mutableListOf(a, b).flatten()
                }
            }
        }
        return zip?.single() ?: listOf()
    }

    override suspend fun getRepoEvents(repo: Repo): List<Event> {
        return gitHubRetrofit.getEvents(repo.owner.login, repo.name)
    }

}