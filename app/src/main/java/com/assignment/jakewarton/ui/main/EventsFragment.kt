package com.assignment.jakewarton.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.assignment.jakewarton.R
import com.assignment.jakewarton.config.REPO_KEY
import com.assignment.jakewarton.databinding.EventsFragmentBinding
import com.assignment.jakewarton.model.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventsFragment : Fragment(R.layout.repo_fragment) {

    companion object {
        fun newInstance(repo: Repo) = EventsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPO_KEY, repo)
            }
        }
    }

    private lateinit var binding : EventsFragmentBinding

    private val viewModel: TributeEventsInfoViewModel by viewModels()

    private lateinit var repo: Repo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repo = arguments?.getParcelable<Repo>(REPO_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventsFragmentBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.eventLiveData.observe(viewLifecycleOwner) { response ->
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
                        ownerUrlValue.text = repo.owner.url
                        ownerUrlValue.setOnClickListener {
                            openURL(repo.owner.url)
                        }
                        repoValue.text = repo.name
                        repoUrlValue.text = repo.htmlUrl
                        repoUrlValue.setOnClickListener {
                            openURL(repo.htmlUrl)
                        }

                        if (response.result.isEmpty().not()) {
                            response.result.sortedBy { event ->
                                event.created.time
                            }.last().apply {
                                eventTypeValue.text = type
                                eventActorValue.text = actor.displayLogin
                                eventActorUrlValue.text = actor.url
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
        viewModel.fetchEvents(repo)
    }

    private fun openURL(url: String) {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(myIntent)
    }
}