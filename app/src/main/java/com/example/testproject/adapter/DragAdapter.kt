package com.example.testproject.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.example.testproject.R
import com.example.testproject.base.TempDragBean
import com.example.testproject.template.DragChildTemplate
import kotlinx.android.synthetic.main.item_drag.view.*

/**
 *
 * @Package: com.example.testproject.adapter
 * @ClassName: DragAdapter
 * @Author: szj
 * @CreateDate: 9/3/21
 */
class DragAdapter(
    layoutResId: Int,
    data: List<TempDragBean>?,
    var isHome: Boolean = false,//是否是主页显示 主页显示显示5条数据
) :
    BaseQuickAdapter<TempDragBean, BaseViewHolder>(layoutResId, data) {

    var childAdapterList = ArrayList<DragChildAdapter>(data!!.size)

    override fun convert(helper: BaseViewHolder, item: TempDragBean) {

        helper.setText(R.id.title, item.title)


        val dragChildTemplate =
            DragChildTemplate(item.child, item.isAddVisibility, item.isAdd, isHome = isHome)
        dragChildTemplate.template(mContext, helper.itemView.childRecyclerView)


        val dragChildAdapter = dragChildTemplate.getAdapter() as DragChildAdapter

        childAdapterList.add(dragChildAdapter)

        //首页不能拖拽
        if (!isHome) {
            //拖拽功能
            initDrag(dragChildAdapter, helper.itemView.childRecyclerView)
        } else {
            //首页隐藏头部标签
            helper.itemView.headGroup.visibility = View.GONE
        }


        //子条目点击
        dragChildAdapter.onItemClick = object : DragChildAdapter.OnDragChildClick {
            override fun imageRightClick(position: Int) {
                itemClick.itemIsAdd(helper.position != 0,
                    helper.position,
                    position,
                    dragChildAdapter,
                    item.child[position]
                )
            }

            override fun childViewClick(position: Int) {
                itemClick.childViewClick(item.child[position])
            }
        }
    }

    // 拖拽
    private fun initDrag(dragChildAdapter: DragChildAdapter, childRecyclerView: RecyclerView) {
        val itemDragAndSwipeCallback = ItemDragAndSwipeCallback(dragChildAdapter)

        val itemTouchHelper = ItemTouchHelper(itemDragAndSwipeCallback)

        //拖拽绑定
        itemTouchHelper.attachToRecyclerView(childRecyclerView)

        // 开启拖拽
        dragChildAdapter.enableDragItem(itemTouchHelper, R.id.image, true)

        //拖拽的监听
        dragChildAdapter.setOnItemDragListener(object : OnItemDragListener {
            override fun onItemDragStart(p0: RecyclerView.ViewHolder?, p1: Int) {
                Log.i("szj正在拖拽1", ":p1:${p1}")
            }

            override fun onItemDragMoving(
                p0: RecyclerView.ViewHolder?,
                p1: Int,
                p2: RecyclerView.ViewHolder?,
                p3: Int,
            ) {
                Log.i("szj正在拖拽2", ":p1:${p1}\tp2:$p2")
            }

            override fun onItemDragEnd(p0: RecyclerView.ViewHolder?, p1: Int) {
                Log.i("szj正在拖拽3", ":p1:${p1}\tp2:")
            }
        })
    }

    interface OnParentClick {

        /**
         * true +, false -
         * parentPosition 组id
         * childPosition 子id
         * dragChildAdapter 当前子适配器
         * childId 子的Id
         */
        fun itemIsAdd(
            isAdd: Boolean,
            parentPosition: Int,
            childPosition: Int,
            dragChildAdapter: DragChildAdapter,
            childData: TempDragBean.childData,
        )

        //整个子View的点击【需要满足条件:不在在编辑环境下才能响应 】
        fun childViewClick(childData: TempDragBean.childData)
    }

    lateinit var itemClick: OnParentClick
}