package com.arya.githubuser.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.presentation.databinding.ItemGithubUserBinding
import com.bumptech.glide.Glide

class ListGitHubUserAdapter(
    private var listGithubUser: List<GithubUserEntity>,
    private val onItemClicked: (GithubUserEntity) -> Unit
) : RecyclerView.Adapter<ListGitHubUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemGithubUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount() = listGithubUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listGithubUser.getOrNull(position)
        holder.bind(user)
    }

    fun updateData(newList: List<GithubUserEntity>) {
        val diffCallback = GitHubUserDiffCallback(listGithubUser, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listGithubUser = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(
        private val binding: ItemGithubUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: GithubUserEntity?) = with(binding) {
            user?.let {
                root.setOnClickListener { onItemClicked(user) }
            }

            tvUsername.text = user?.login
            Glide.with(root.context).load(user?.avatarUrl).into(ivPicture)
        }
    }
}