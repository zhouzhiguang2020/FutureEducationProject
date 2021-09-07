package com.example.testproject.adapter

import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.testproject.R
import com.example.testproject.base.TempDragBean
import com.example.testproject.utils.click
import kotlinx.android.synthetic.main.item_drag_child.view.*

/**
 *
 * @Package: com.example.testproject.adapter
 * @ClassName: DragChildAdapter
 * @Author: szj
 * @CreateDate: 9/3/21
 */
class DragChildAdapter(
    layoutResId: Int,
    data: List<TempDragBean.childData>?,
    var isAddVisibility: Boolean,   //是否显示+-
    var isAdd: Boolean, //  true + ,false -
) :
    BaseItemDraggableAdapter<TempDragBean.childData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: TempDragBean.childData) {

        Log.i("szjTitle",item.title)

        helper.setText(R.id.tv, item.title)

        helper.itemView.image.setImageResource(item.drawable)

        val imageRight = helper.itemView.imageRight

        //右上角+- 是否显示
        imageRight.visibility = if (isAddVisibility) View.VISIBLE else View.GONE

        //设置image图片
        imageRight.setImageResource(if (isAdd) R.drawable.ic_baseline_add_24 else R.drawable.ic_baseline_remove_24)


        //+- 点击
        imageRight.click {
            onItemClick.imageRightClick(helper.position)
        }

//
        helper.itemView.image.click {
            //在加号减号隐藏状态下才能点击子View
            if (!isAddVisibility) {
                onItemClick.childViewClick(helper.position)
            }
        }

//        helper.itemView.image.setOnClickListener {
//
//        }


    }

    interface OnDragChildClick {
        //加号 减号的点击
        fun imageRightClick(position: Int)

        //整个子View的点击
        fun childViewClick(position: Int)

    }

    lateinit var onItemClick: OnDragChildClick
}

