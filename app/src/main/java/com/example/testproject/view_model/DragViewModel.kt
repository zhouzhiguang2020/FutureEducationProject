package com.example.testproject.view_model

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.testproject.R
import com.example.testproject.base.TempDragBean
import com.example.testproject.dao.DBInstance.getInstance
import com.example.testproject.dao.DragDaoData
import com.example.testproject.utils.toast

/**
 *
 * @Package: com.example.testproject.view_model
 * @ClassName: DragViewModel
 * @Author: szj
 * @CreateDate: 9/3/21
 */
class DragViewModel : ViewModel() {

    var liveData = MutableLiveData<List<TempDragBean>>()

    //是否是首页 首页不显示更多
    var isHome = false

    //获取所有数据【第一次使用默认数据】
    fun getData(context: Context): ArrayList<TempDragBean> {
        //dao
        val dragDaoData = DragDaoData(getInstance(context))

        // 获取数据库中的数据，赋值给liveData
        liveData.value = dragDaoData.all()

        return liveData.value as ArrayList<TempDragBean>
    }

    //更新数据库中的数据
    fun upData(context: Context, dataList: ArrayList<TempDragBean>) {
        //dao
        val dragDaoData = DragDaoData(getInstance(context))

        //更新数据库中的数据
        dragDaoData.upData(dataList)
    }


    /**
     * TODO 跳转页面
     */
    fun actionJump(
        activity: Activity,
        childData: TempDragBean.childData,
        view: View,
    ) {
        when (childData.title) {
            activity.getString(R.string.more) -> {
                Navigation.findNavController(view).navigate(R.id.dragDetailsFragment)
            }

            else -> childData.title.toast(activity)
        }
    }

}