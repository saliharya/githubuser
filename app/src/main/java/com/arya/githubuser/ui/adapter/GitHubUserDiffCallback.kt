package com.arya.githubuser.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.arya.githubuser.model.GithubUser

class GitHubUserDiffCallback(
    private val oldList: List<GithubUser>,
    private val newList: List<GithubUser>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
