package com.arya.githubuser.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, VM : ViewModel> : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val viewModel: VM
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    abstract fun setupViews()
    abstract fun observeData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}