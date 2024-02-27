package com.arya.githubuser.common.utils

import android.app.Activity
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun Activity.showToast(text: String?) {
    if (text.isNullOrEmpty()) return
    MotionToast.createToast(
        context = this,
        title = "",
        message = text,
        style = MotionToastStyle.WARNING,
        position = MotionToast.GRAVITY_BOTTOM,
        duration = MotionToast.SHORT_DURATION,
        font = null
    )
}