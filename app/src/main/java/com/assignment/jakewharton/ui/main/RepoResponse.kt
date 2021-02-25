package com.assignment.jakewharton.ui.main
import com.assignment.jakewharton.model.Repo

sealed class RepoResponse {

    object Loading: RepoResponse()
    data class Success(val result: List<Repo>): RepoResponse()
    data class Error(val message: String): RepoResponse()
}