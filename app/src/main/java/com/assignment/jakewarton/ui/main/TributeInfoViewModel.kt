package com.assignment.jakewarton.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.assignment.jakewarton.config.GITHUB_OWNERS
import com.assignment.jakewarton.data.GitHubRepository
import kotlinx.coroutines.launch

class TributeInfoViewModel
    @ViewModelInject
    constructor(
        private val repository: GitHubRepository
    ) : ViewModel() {

    private val reposMutableLiveData = MutableLiveData<RepoResponse>()

    val reposLiveData : LiveData<RepoResponse>
        get() = reposMutableLiveData

    fun fetchRepos() {
        viewModelScope.launch {
            reposMutableLiveData.value = RepoResponse.Loading
            try {
                val response = repository.getMultipleRepos(
                    GITHUB_OWNERS
                )
                reposMutableLiveData.value = RepoResponse.Success(response)
            } catch(exception: Exception) {
                reposMutableLiveData.value = RepoResponse.Error(exception.message!!)
            }
        }
    }
}