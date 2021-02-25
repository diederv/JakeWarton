package com.assignment.jakewharton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.jakewharton.databinding.RepoListItemBinding
import com.assignment.jakewharton.model.Repo

class RepoListAdapter(private val onClick: (Repo) -> Unit):
    RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    private val items = ArrayList<Repo>()

    fun setItems(items: List<Repo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    class RepoViewHolder(val binding: RepoListItemBinding, val onClick: (Repo) -> Unit):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(repo: Repo) {
                binding.repo.setOnClickListener {
                    onClick(repo)
                }
                binding.owner.text = repo.owner.login
                binding.name.text = repo.name
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = RepoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = items.get(position)
        holder.bind(repo)
    }
}