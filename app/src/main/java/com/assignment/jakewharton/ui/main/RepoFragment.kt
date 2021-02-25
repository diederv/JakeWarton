package com.assignment.jakewharton.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.jakewharton.EventsActivity
import com.assignment.jakewharton.R
import com.assignment.jakewharton.adapter.RepoListAdapter
import com.assignment.jakewharton.config.REPO_KEY
import com.assignment.jakewharton.databinding.RepoFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepoFragment : Fragment(R.layout.repo_fragment) {

    companion object {
        fun newInstance() = RepoFragment()
    }

    private lateinit var binding : RepoFragmentBinding

    private val viewModel: TributeInfoViewModel by viewModels()

    val repoListAdapter = RepoListAdapter() { repo ->
        val intent = Intent(requireContext(), EventsActivity::class.java).apply {
            putExtra(REPO_KEY, repo)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepoFragmentBinding.inflate(layoutInflater).apply {
            recyclerView.adapter = repoListAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.reposLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RepoResponse.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.error.visibility = View.GONE
                }
                is RepoResponse.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.error.visibility = View.GONE
                    repoListAdapter.setItems(response.result)
                }
                is RepoResponse.Error -> {
                    binding.error.text = response.message
                    binding.error.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                }
            }
        }
        viewModel.fetchRepos()
    }
}