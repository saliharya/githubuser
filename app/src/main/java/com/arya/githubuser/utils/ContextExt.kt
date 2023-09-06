package com.arya.githubuser.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String?) {
    if (text.isNullOrEmpty()) return
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}