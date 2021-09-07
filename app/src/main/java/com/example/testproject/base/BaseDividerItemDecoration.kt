package com.example.testproject.base

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @Package: cn.droidlover.xdroidmvp.base
 * @ClassName: BaseDividerItemDecoration
 * @Author: szj
 * @CreateDate: 7/8/21 11:33 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class BaseDividerItemDecoration(context: Context?, orientation: Int) :
    DividerItemDecoration(context, orientation) {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = 10
        outRect.right = 10
        outRect.bottom = 5
    }
}