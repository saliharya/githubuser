package com.arya.githubuser.utils

import android.content.SharedPreferences

object ThemeUtils {
    fun isDarkModeEnabled(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean("dark_mode", false)
    }
}