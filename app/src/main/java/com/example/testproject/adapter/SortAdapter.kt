package com.example.testproject.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.example.testproject.R
import com.example.testproject.bean.SortBean
import kotlinx.android.synthetic.main.item.view.*

/*
 * 
 * @Package: com.example.testproject.adapter
 * @ClassName: SortAdapter
 * 作者: android super soldier
 * @CreateDate: 2021/10/9
 * TODO gitee下载地址:https://gitee.com/lanyangyangzzz/kotlin_open_eye
 * CSDN 博客:https://blog.csdn.net/weixin_44819566
 * 掘金博客:https://juejin.cn/user/2251439606079277
 * TODO 排序适配器
 */
class SortAdapter : RecyclerView.Adapter<SortAdapter.SortViewHolder>() {

    lateinit var sortList: SortedList<SortBean>


    inner class SortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        return SortViewHolder(View.inflate(parent.context, R.layout.item, null))
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        holder.itemView.tv.text = sortList[position].toString()
    }

    override fun getItemCount(): Int = sortList.size()

    /*
     * 作者: android 超级兵
     * 时间: 2021/10/9 13:12
     * TODO: 添加所有集合
     */
    fun addAll(list: List<SortBean>) {
        sortList.beginBatchedUpdates()

        sortList.addAll(list)

        sortList.endBatchedUpdates()
    }

    /*
     * 作者: android 超级兵
     * 时间: 2021/10/9 13:11
     * TODO: 添加一个元素
     */
    fun setData(sortBean: SortBean) {
        sortList.add(sortBean)
    }

    /*
     * 作者: android 超级兵
     * 时间: 2021/10/9 13:11
     * TODO: 根据下标删除某个元素
     */
    fun deleteData(index: Int) {
        sortList.removeItemAt(index)
    }

    /*
     * 作者: android 超级兵
     * 时间: 2021/10/9 13:11
     * TODO: 删除集合中的某个对象
     */
    fun deleteData(data: SortBean) {
        sortList.remove(data)
    }
}