package com.arya.githubuser.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.arya.githubuser.core.domain.model.GithubUserEntity

class GitHubUserDiffCallback(
    private val oldList: List<GithubUserEntity>,
    private val newList: List<GithubUserEntity>
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
