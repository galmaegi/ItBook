package com.example.itbook.common.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.Fragment

fun Context.getActivityContext(): Activity? =
    when (this) {
        is Activity -> this
        is Fragment -> this.activity
        is ContextWrapper -> this.baseContext.getActivityContext()
        else -> null
    }
