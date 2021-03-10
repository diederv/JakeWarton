package com.assignment.jakewharton

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.assignment.jakewharton.config.REPO_KEY
import com.assignment.jakewharton.databinding.EventsActivityBinding
import com.assignment.jakewharton.model.Repo
import com.assignment.jakewharton.viewmodel.EventResponse
import com.assignment.jakewharton.viewmodel.TributeEventsInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.net.URL

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventsActivity : AppCompatActivity() {

    private lateinit var binding : EventsActivityBinding

    private val viewModel: TributeEventsInfoViewModel by viewModels()

    private lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = intent.getParcelableExtra<Repo>(REPO_KEY)!!
        setTitle(R.string.events_title)

        binding = EventsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        viewModel.fetchEvents(repo)
    }

    private fun observeViewModel() {
        viewModel.eventLiveData.observe(this) { response ->
            when (response) {
                is EventResponse.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.error.visibility = View.GONE
                }
                is EventResponse.Success -> {
                    binding.apply {
                        progress.visibility = View.GONE
                        error.visibility = View.GONE
                        ownerValue.text = repo.owner.login
                        ownerUrlValue.text = crop(repo.owner.url)
                        ownerUrlValue.setOnClickListener {
                            openURL(repo.owner.url)
                        }
                        repoValue.text = repo.name
                        repoUrlValue.text = crop(repo.htmlUrl)
                        repoUrlValue.setOnClickListener {
                            openURL(repo.htmlUrl)
                        }

                        if (response.result.isEmpty().not()) {
                            response.result.sortedBy { event ->
                                event.created.time
                            }.last().apply {
                                eventTypeValue.text = type
                                eventActorValue.text = actor.displayLogin
                                eventActorUrlValue.text = crop(actor.url)
                                eventActorUrlValue.setOnClickListener {
                                    openURL(actor.url)
                                }
                            }
                        }
                    }
                }
                is EventResponse.Error -> {
                    binding.error.text = response.message
                    binding.error.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    private fun crop(urlString: String): String {
        return URL(urlString).path
    }

    private fun openURL(url: String) {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(myIntent)
    }
}