package com.assignment.jakewharton.viewmodel
import com.assignment.jakewharton.model.Event

sealed class EventResponse {

    object Loading: EventResponse()
    data class Success(val result: List<Event>): EventResponse()
    data class Error(val message: String): EventResponse()
}