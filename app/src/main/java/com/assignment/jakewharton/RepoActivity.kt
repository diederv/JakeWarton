package com.assignment.jakewharton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.jakewharton.adapter.RepoListAdapter
import com.assignment.jakewharton.config.GITHUB_OWNERS
import com.assignment.jakewharton.config.REPO_KEY
import com.assignment.jakewharton.databinding.RepoActivityBinding
import com.assignment.jakewharton.viewmodel.RepoResponse
import com.assignment.jakewharton.viewmodel.TributeInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepoActivity : AppCompatActivity() {

    private lateinit var binding : RepoActivityBinding

    private val viewModel: TributeInfoViewModel by viewModels()

    val repoListAdapter = RepoListAdapter() { repo ->
        val intent = Intent(this, EventsActivity::class.java).apply {
            putExtra(REPO_KEY, repo)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(GITHUB_OWNERS.map { s -> s.capitalize() }.reduceRight { a, b -> "Repos of $a & $b"})

        binding = RepoActivityBinding.inflate(layoutInflater).apply {
            recyclerView.adapter = repoListAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@RepoActivity)
        }
        setContentView(binding.root)

        observeViewModel()
        viewModel.fetchRepos()
    }

    private fun observeViewModel() {
        viewModel.reposLiveData.observe(this) { response ->
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
    }
}