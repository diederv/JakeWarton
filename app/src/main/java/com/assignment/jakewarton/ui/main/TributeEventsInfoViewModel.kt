package com.assignment.jakewarton.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.assignment.jakewarton.data.GitHubRepository
import com.assignment.jakewarton.model.Repo
import kotlinx.coroutines.launch

class TributeEventsInfoViewModel
    @ViewModelInject
    constructor(
        private val repository: GitHubRepository
    ) : ViewModel() {

    private val eventMutableLiveData = MutableLiveData<EventResponse>()

    val eventLiveData : LiveData<EventResponse>
        get() = eventMutableLiveData

    fun fetchEvents(repo: Repo) {
        viewModelScope.launch {
            eventMutableLiveData.value = EventResponse.Loading
            try {
                val response = repository.getRepoEvents(repo)
                eventMutableLiveData.value = EventResponse.Success(response)
            } catch(exception: Exception) {
                eventMutableLiveData.value = EventResponse.Error(exception.message!!)
            }
        }
    }
}