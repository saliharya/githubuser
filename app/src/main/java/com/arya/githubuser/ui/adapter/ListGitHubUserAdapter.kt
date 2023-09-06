package com.arya.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arya.githubuser.databinding.ItemGithubUserBinding
import com.arya.githubuser.model.GithubUser
import com.bumptech.glide.Glide

class ListGitHubUserAdapter(
    private val listGithubUser: ArrayList<GithubUser>,
    private val onItemClicked: (GithubUser) -> Unit
) : RecyclerView.Adapter<ListGitHubUserAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: GithubUser) {
            with(binding) {
                tvUsername.text = user.login // Set the username

                // Load the user's avatar using Glide (you may need to add the Glide dependency to your project)
                Glide.with(root.context)
                    .load(user.avatar_url)
                    .into(ivPicture)

                // Set a click listener for the item, if needed
                root.setOnClickListener { onItemClicked(user) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemGithubUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listGithubUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listGithubUser[position]
        holder.bind(user)
    }
}
