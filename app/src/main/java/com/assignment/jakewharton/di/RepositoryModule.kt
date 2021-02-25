package com.assignment.jakewharton.di

import com.assignment.jakewharton.data.GitHubRepository
import com.assignment.jakewharton.data.GitHubRepositoryImpl
import com.assignment.jakewharton.data.GitHubRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGitHubRepository(
        gitHubRetrofit: GitHubRetrofit
    ): GitHubRepository = GitHubRepositoryImpl(gitHubRetrofit)

}