package com.assignment.jakewharton

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.jakewharton.adapter.RepoListAdapter
import com.assignment.jakewharton.data.GitHubRepository
import com.assignment.jakewharton.data.GitHubRetrofit
import com.assignment.jakewharton.di.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton


@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)

@HiltAndroidTest
class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    var activityScenarioRule: ActivityScenarioRule<RepoActivity> =
        ActivityScenarioRule<RepoActivity>(
            RepoActivity::class.java
        )

    @Module
    @InstallIn(ApplicationComponent::class)
    abstract class TestModule {

        @Singleton
        @Provides
        fun provideGitHubRepository(
            gitHubRetrofit: GitHubRetrofit
        ): GitHubRepository = FakeGitHubRepository.provideGitHubRepository()
    }

    @Test
    fun testThatTheMaincontainerIsVisible() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun testAndSelectTheFirstRepoAndOpenItsDetailScreen() {
//        ActivityScenario.launch(RepoActivity::class.java)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(0, ViewActions.click())
        )
        onView(withText("displayLogin1")).check(matches(isDisplayed()))
    }
}