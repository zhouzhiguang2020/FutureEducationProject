package com.example.testproject.template

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.BaseAnimation
import com.example.testproject.R
import com.example.testproject.adapter.DragAdapter
import com.example.testproject.base.BaseRecyclerViewTemplate
import com.example.testproject.base.TempDragBean

/**
 *
 * @Package: com.example.testproject.template
 * @ClassName: DragTemplate
 * @Author: szj
 * @CreateDate: 9/3/21
 */
class DragTemplate(
    private val list: List<TempDragBean>,
    var isHome: Boolean = false,//是否是主页显示 主页显示显示5条数据
) :BaseRecyclerViewTemplate() {

    private lateinit var adapter: DragAdapter

    override fun buildAdapter(): BaseQuickAdapter<*, *> =
        DragAdapter(R.layout.item_drag, list,
            isHome = isHome
        ).apply {
            adapter = this
        }

    override fun getAdapter(): RecyclerView.Adapter<*> = adapter

    //不执行动画
    override fun getAnimation(): BaseAnimation = BaseAnimation { arrayOfNulls(0) }
}