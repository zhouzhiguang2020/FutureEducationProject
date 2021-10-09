package com.example.testproject.call

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.testproject.bean.SortBean

/*
 * 
 * @Package: com.example.testproject.call
 * @ClassName: SortListCallBack
 * 作者: android super soldier
 * @CreateDate: 2021/10/9
 * TODO gitee下载地址:https://gitee.com/lanyangyangzzz/kotlin_open_eye
 * CSDN 博客:https://blog.csdn.net/weixin_44819566
 * 掘金博客:https://juejin.cn/user/2251439606079277
 * TODO 排序
 */
class SortListCallBack(adapter: RecyclerView.Adapter<*>) :
    SortedListAdapterCallback<SortBean>(adapter) {
    /*
     * [用来排序 排序条件]
     * 作者: android 超级兵
     * 时间: 2021/10/9 09:50
     *
     *        如果指定的数与参数相等返回0。
     *        如果小于其他值，则返回负数；
     *        如果大于其他值，则返回正数。
     */
    override fun compare(o1: SortBean, o2: SortBean): Int = let {
        // 从大到小
        // o2.id.compareTo(o1.id)
        // 从小到大
        o1.id.compareTo(o2.id)
        // 根据字母排序
        // o1.abbreviation.compareTo(o2.abbreviation)
    }

    /*  [用来去重]
    * 作者: android 超级兵
    * 时间: 2021/10/9 09:53
    */
    override fun areItemsTheSame(item1: SortBean, item2: SortBean)
            : Boolean = let {
        item1.abbreviation == item2.abbreviation
    }

    /*
     * 作者: android 超级兵
     * 时间: 2021/10/9 09:52
     * TODO: 用来判断两个item是否相同
     *
     *  参数一: oldItem–对象的上一个表示形式。
        参数二: newItem–替换上一个对象的新对象。
        返回：如果项目内容相同，则为True；如果项目内容不同，则为false。
        *
        * areContentsTheSame()是取代了equals方法，用于判断SortedList中Item是否改变，如果改变则调用onChanged()函数
     */
    override fun areContentsTheSame(oldItem: SortBean, newItem: SortBean): Boolean = let {
        Log.i(
            "szjAreContentsTheSame",
            "01:${oldItem} \t 02:${newItem} == ${oldItem.hashCode() == newItem.hashCode()}"
        )
        oldItem.hashCode() == newItem.hashCode()
    }


}