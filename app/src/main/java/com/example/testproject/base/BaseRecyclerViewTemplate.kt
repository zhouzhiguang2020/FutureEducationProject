package com.example.testproject.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.chad.library.adapter.base.animation.BaseAnimation
import com.example.testproject.R

/**
 *
 * @ClassName: BaseRecyclerViewTemplate
 * @Author: szj
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * TODO RecyclerView 和 万能适配器[BaseQuickAdapter] 模版模式封装
 */
abstract class BaseRecyclerViewTemplate {

    private var adapter: BaseQuickAdapter<*, *>? = null

    @SuppressLint("InflateParams")
    open fun template(context: Context?, rel: RecyclerView) {
        //创建管理器
        rel.layoutManager = buildLayoutManager(context)

        //创建适配器
        adapter = buildAdapter()
        if (adapter != null) {
            //绑定适配器
            rel.adapter = adapter

            //设置动画
            // BaseAnimation { arrayOfNulls(0) } 不显示动画
            adapter?.openLoadAnimation(getAnimation())

            //动画是否重复执行
            adapter!!.isFirstOnly(isAnimationRepeat())
        }

        //是否绘制水平线 水平
        if (isItemDecorationHorizontal()) {
            rel.addItemDecoration(
                BaseDividerItemDecoration(
                    context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
        //是否绘制水平线 垂直
        if (isItemDecorationVertical()) {
            rel.addItemDecoration(
                BaseDividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        if (isEmptyVisibility()) {
            //空布局
            adapter?.emptyView = LayoutInflater.from(context).inflate(
                R.layout.no_layout,
                null,
                false
            )
        }

        if (headView(context) != null) {
            adapter?.addHeaderView(headView(context))
        }


        if (bottomView(context) != null) {
            adapter?.addFooterView(bottomView(context),1)
        }
    }


    /**
     * 创建适配器
     */
    protected abstract fun buildAdapter(): BaseQuickAdapter<*, *>?

    /**
     * 创建管理器
     */
    open fun buildLayoutManager(context: Context?): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    /**
     * 设置渐变动画
     */
   open fun getAnimation(): BaseAnimation = AlphaInAnimation()

    /**
     * 动画是否重复执行
     *
     * @return false重复执行  默认不重复执行
     */
    open fun isAnimationRepeat(): Boolean {
        return true
    }

    /**
     * 空布局是否显示 true显示(默认)  false不显示
     */
    open fun isEmptyVisibility(): Boolean {
        return true
    }

    /**
     *是否绘制水平线 水平(默认不绘制)
     */
    open fun isItemDecorationHorizontal(): Boolean {
        return false
    }

    /**
     *是否绘制水平线 垂直(默认不绘制)
     */
    open fun isItemDecorationVertical(): Boolean {
        return false
    }

    /**
     * 获取当前适配器
     */
    abstract fun getAdapter(): RecyclerView.Adapter<*>

    //顶部布局
    open fun headView(context: Context?): View? {
        return null
    }

    //底部布局
    open fun bottomView(context: Context?): View? {
        return null
    }
}