package com.example.itbook.common.extensions

import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter

@BindingAdapter("app:bindObject")
fun Group.bindObject(string: String?) {
    this.visibility =
        if (string.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
}