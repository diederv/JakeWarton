package com.assignment.jakewarton.ui.main
import com.assignment.jakewarton.model.Repo

sealed class RepoResponse {

    object Loading: RepoResponse()
    data class Success(val result: List<Repo>): RepoResponse()
    data class Error(val message: String): RepoResponse()
}