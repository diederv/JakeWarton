package com.assignment.jakewharton

import com.assignment.jakewharton.config.DATE_FORMAT
import com.assignment.jakewharton.data.GitHubRepository
import com.assignment.jakewharton.model.Actor
import com.assignment.jakewharton.model.Event
import com.assignment.jakewharton.model.Owner
import com.assignment.jakewharton.model.Repo
import java.text.SimpleDateFormat

class FakeGitHubRepository {

    companion object {

        fun provideGitHubRepository(): GitHubRepository = object : GitHubRepository {

            override suspend fun getRepos(user: String): List<Repo> {
                return when (user) {
                    "JakeWharton" -> listOf(
                        Repo(1, "repo1", "Repository 1", Owner(1, "login1", "url1"), "htmlUrl1"),
                        Repo(2, "repo2", "Repository 2", Owner(2, "login2", "url2"), "htmlUrl2")
                    )
                    else -> listOf(
                        Repo(3, "repo3", "Repository 3", Owner(3, "login3", "url3"), "htmlUrl3"),
                        Repo(4, "repo4", "Repository 4", Owner(4, "login4", "url4"), "htmlUrl4")
                    )
                }
            }

            override suspend fun getMultipleRepos(users: List<String>): List<Repo> {
                return listOf(
                    Repo(5, "repo5", "Repository 5", Owner(5, "login5", "url5"), "htmlUrl5"),
                    Repo(6, "repo6", "Repository 6", Owner(6, "login6", "url6"), "htmlUrl6")
                )
            }

            override suspend fun getRepoEvents(repo: Repo): List<Event> {
                return listOf(
                    Event(
                        1,
                        "type",
                        Actor(1, "url1", "type1", "displayLogin1"),
                        SimpleDateFormat(DATE_FORMAT).parse("2021-02-24T11:50:17Z")!!
                    )
                )
            }
        }
    }

}