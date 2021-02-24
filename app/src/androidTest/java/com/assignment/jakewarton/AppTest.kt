package com.assignment.jakewarton

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.jakewarton.data.GitHubRepository
import com.assignment.jakewarton.data.GitHubRetrofit
import com.assignment.jakewarton.di.RepositoryModule
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
    fun happyPath() {
        ActivityScenario.launch(RepoActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            ViewActions.click()
            //RecyclerViewActions.actionOnItemAtPosition(0, click())
        )
    }
}