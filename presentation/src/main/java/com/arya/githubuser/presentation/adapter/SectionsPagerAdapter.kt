package com.arya.githubuser.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arya.githubuser.presentation.fragment.FollowerFragment
import com.arya.githubuser.presentation.fragment.FollowingFragment

class SectionsPagerAdapter(
    activity: AppCompatActivity, private val username: String
) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.newInstance(username)
            1 -> fragment = FollowerFragment.newInstance(username)
        }
        return fragment as Fragment
    }

}