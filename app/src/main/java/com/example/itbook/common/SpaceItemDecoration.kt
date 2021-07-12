package com.example.itbook.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(
    private val verticalSpaceHeight: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeight
    }
}

class HorizontalSpaceItemDecoration(
    private val horizontalSpaceWidth: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = horizontalSpaceWidth
    }

}

val BOOK_SPACE_DECORATION by lazy { VerticalSpaceItemDecoration(20) }
val HISTORY_SPACE_DECORATION by lazy { HorizontalSpaceItemDecoration(10) }