package com.assignment.jakewharton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assignment.jakewharton.config.REPO_KEY
import com.assignment.jakewharton.model.Repo
import com.assignment.jakewharton.ui.main.EventsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.events_title)
        setContentView(R.layout.events_activity)

        intent?.also { intent ->
            val repo = intent.getParcelableExtra<Repo>(REPO_KEY)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EventsFragment.newInstance(repo!!))
                .commitNow()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}