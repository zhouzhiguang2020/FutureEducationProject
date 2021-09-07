package com.example.testproject.template

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.BaseAnimation
import com.example.testproject.R
import com.example.testproject.adapter.DragChildAdapter
import com.example.testproject.base.BaseRecyclerViewTemplate
import com.example.testproject.base.TempDragBean

/**
 *
 * @Package: com.example.testproject.template
 * @ClassName: DragChildTemplate
 * @Author: szj
 * @CreateDate: 9/3/21
 */
class DragChildTemplate(
    private val list: List<TempDragBean.childData>,
    private val isAddVisibility: Boolean,   //是否显示+-
    private val isAdd: Boolean, //  true + ,false -
    var isHome: Boolean = false,//是否是主页显示 主页显示显示5条数据
) : BaseRecyclerViewTemplate() {

    private lateinit var adapter: DragChildAdapter

    override fun buildAdapter(): BaseQuickAdapter<*, *> =
        DragChildAdapter(R.layout.item_drag_child, list, isAddVisibility, isAdd)
            .apply { adapter = this }

    override fun getAdapter(): RecyclerView.Adapter<*> = adapter

    override fun buildLayoutManager(context: Context?): RecyclerView.LayoutManager {
        return GridLayoutManager(context, if (isHome) 5 else 4)
    }

    //不执行动画
    override fun getAnimation(): BaseAnimation = BaseAnimation { arrayOfNulls(0) }
}