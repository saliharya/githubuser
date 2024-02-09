package com.arya.githubuser.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding, VM: ViewModel>: AppCompatActivity() {
    abstract val binding: T
    abstract val viewModel: VM
    abstract fun setupViews()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        observeData()
    }
}