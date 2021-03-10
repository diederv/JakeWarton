package com.assignment.jakewharton.viewmodel
import com.assignment.jakewharton.model.Repo

sealed class RepoResponse {

    object Loading: RepoResponse()
    data class Success(val result: List<Repo>): RepoResponse()
    data class Error(val message: String): RepoResponse()
}