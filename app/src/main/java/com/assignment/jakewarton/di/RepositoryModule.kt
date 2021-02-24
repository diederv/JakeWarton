package com.assignment.jakewarton.di

import com.assignment.jakewarton.data.GitHubRepository
import com.assignment.jakewarton.data.GitHubRepositoryImpl
import com.assignment.jakewarton.data.GitHubRetrofit
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