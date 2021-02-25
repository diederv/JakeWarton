package com.assignment.jakewharton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assignment.jakewharton.config.GITHUB_OWNERS
import com.assignment.jakewharton.ui.main.RepoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(GITHUB_OWNERS.map { s -> s.capitalize() }.reduceRight { a, b -> "Repos of $a & $b"})
        setContentView(R.layout.repo_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RepoFragment.newInstance())
                .commitNow()
        }
    }
}