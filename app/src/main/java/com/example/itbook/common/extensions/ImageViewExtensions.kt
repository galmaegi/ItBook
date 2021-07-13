package com.example.itbook.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.itbook.R
import java.io.ByteArrayOutputStream

@BindingAdapter("app:bindImage")
fun ImageView.bindImage(url: String?) {
    Glide.with(context)
        .load(url)
        .fitCenter()
        .placeholder(R.drawable.loading_example)
        .into(this)
}

fun ImageView.getCompressedRawBitmap(): ByteArray? = (drawable as? BitmapDrawable)?.let {
    ByteArrayOutputStream().apply {
        it.bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
    }.toByteArray()
}

fun ImageView.setCompressedRawBitmap(array: ByteArray) {
    setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.size))
}

