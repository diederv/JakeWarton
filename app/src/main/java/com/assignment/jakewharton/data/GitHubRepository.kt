package com.assignment.jakewharton.data

import com.assignment.jakewharton.model.Event
import com.assignment.jakewharton.model.Repo

interface GitHubRepository {

    /*
     * @param user the name of the GitHub user
     * @return the list of repositories of the given user
     */
    suspend fun getRepos(user: String): List<Repo>

    /*
    * @param users the list of names of the GitHub users
    * @return the list of repositories of the given users combined
    */
    suspend fun getMultipleRepos(users: List<String>): List<Repo>

    /*
    * @param repo the repository object for which the events are requested
    * @return the list of events of the given repository
    */
    suspend fun getRepoEvents(repo: Repo): List<Event>

}