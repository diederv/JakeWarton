package com.assignment.jakewharton

import com.assignment.jakewharton.data.GitHubRepository
import com.assignment.jakewharton.data.GitHubRepositoryImpl
import com.assignment.jakewharton.data.GitHubRetrofit
import com.assignment.jakewharton.model.Event
import com.assignment.jakewharton.model.Repo
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Test

class GitHubRepositoryImplTest {

    private fun getGitHubRepository(): GitHubRepository = FakeTestGitHubRepository.provideGitHubRepository()

    private fun getGitHubRepositoryImpl(): GitHubRepository {
        return GitHubRepositoryImpl(
            object: GitHubRetrofit {

                override suspend fun listRepos(user: String): List<Repo> {
                    return FakeTestGitHubRepository.provideGitHubRepository().getRepos(user)
                }

                override suspend fun getEvents(user: String, repo: String): List<Event> {
                    val repos = FakeTestGitHubRepository.provideGitHubRepository().getRepos(user)
                    return FakeTestGitHubRepository.provideGitHubRepository().getRepoEvents(
                        repos.first { it.name == repo }
                    )
                }
            }
        )
    }

    @Test
    fun testGetRepos() {
        val gitHubRepository = getGitHubRepositoryImpl()
        val repos = runBlocking { gitHubRepository.getRepos("JakeWharton") }
        assertEquals(repos.first().name, "repo1")
    }

    @Test
    fun testgGetMultipleRepos() {

        val gitHubRepository = getGitHubRepositoryImpl()
        val repos = runBlocking { gitHubRepository.getMultipleRepos(listOf("JakeWharton", "infinum")) }

        assertEquals(4, repos.size)
        assertEquals("repo1", repos.first().name)
        assertEquals("repo2", repos.get(1).name)
        assertEquals("repo3", repos.get(2).name)
        assertEquals("repo4", repos.get(3).name)
    }
}