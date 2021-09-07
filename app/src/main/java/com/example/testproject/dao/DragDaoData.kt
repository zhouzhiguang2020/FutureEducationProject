package com.example.testproject.dao

import android.util.Log
import com.example.testproject.R
import com.example.testproject.base.TempDragBean
import com.example.testproject.utils.gson_convert.BaseListConvert

/**
 *
 * @Package: com.example.testproject.dao
 * @ClassName: DragDaoData
 * @Author: szj
 * @CreateDate: 9/6/21
 * TODO 数据库数据
 */
class DragDaoData(private val appDataBase: AppDataBase) {

    /**
     * 所有数据
     */
    fun all(): List<TempDragBean> {
        //获取数据库中的所有数据
        val allData = appDataBase.getDao().getAll()

        //临时存储list
        val tempDragTestBeanList = ArrayList<TempDragBean>()

        //Gson <==> ArrayList<T> 转换器
        val baseListConvert = BaseListConvert<DragBean>()

        allData.forEach {
            val dragTestBeanChildList = ArrayList<TempDragBean.childData>()

            val revert = baseListConvert.revert(it.child, childData::class.java)
            revert.forEach {
                dragTestBeanChildList.add(TempDragBean.childData(it.childTitle!!,
                    it.childDrawable,
                    it.childParentId))
            }

            tempDragTestBeanList.add(TempDragBean(it.title!!,
                it.isAdd!!,
                it.isAddVisibility!!,
                dragTestBeanChildList))
        }

        Log.i("szj数据库中的数据为:", "${tempDragTestBeanList.size}")
        //第一次的时候 dragTestBeanList== 0 所以使用默认数据
        if (tempDragTestBeanList.size <= 0) {
            tempDragTestBeanList.addAll(initData())
        }
        return tempDragTestBeanList

    }

    // 更新数据
    fun upData(data:ArrayList<TempDragBean>) {
        // 清空所有数据
        appDataBase.clearAllTables()

        val convert = BaseListConvert<TempDragBean.childData>()

        data.forEach {
            val tempList = ArrayList<childData>()

            it.child.forEach {
                tempList.add(childData(it.title, it.drawable, it.parentId))
            }

            Log.i("szjInstance", it.title)
            appDataBase.getDao().instance(DragBean(it.title,
                it.isAdd,
                it.isAddVisibility,
                convert.converter(tempList)))
        }

    }

    // 第一次执行默认的数据
    private fun initData() = let {
        val dragTestBeanList = arrayListOf(
            TempDragBean("首页常用功能", false, false,
                arrayListOf(
                    TempDragBean.childData("绿色1", R.drawable.drawable_green, 1),
                    TempDragBean.childData("黄色1", R.drawable.drawable_yellow, 3),
                    TempDragBean.childData("蓝色5", R.drawable.green_blue, 4),
                )),

            TempDragBean("其他功能", true, false,
                arrayListOf(
                    TempDragBean.childData("绿色2", R.drawable.drawable_green, 1),
                    TempDragBean.childData("绿色3", R.drawable.drawable_green, 1),
                )),

            TempDragBean("安全功能", true, false,
                arrayListOf(
                    TempDragBean.childData("红色2", R.drawable.drawable_red, 2),
                    TempDragBean.childData("红色3", R.drawable.drawable_red, 2),
                    TempDragBean.childData("红色4", R.drawable.drawable_red, 2),
                )),

            TempDragBean("职业功能", true, false,
                arrayListOf(
                    TempDragBean.childData("黄色2", R.drawable.drawable_yellow, 3),
                    TempDragBean.childData("黄色3", R.drawable.drawable_yellow, 3),
                    TempDragBean.childData("黄色4", R.drawable.drawable_yellow, 3),
                    TempDragBean.childData("黄色5", R.drawable.drawable_yellow, 3),
                    TempDragBean.childData("黄色6", R.drawable.drawable_yellow, 3),
                    TempDragBean.childData("黄色7", R.drawable.drawable_yellow, 3),
                )),

            TempDragBean("test", true, false,
                arrayListOf(
                    TempDragBean.childData("蓝色1", R.drawable.green_blue, 4),
                    TempDragBean.childData("蓝色2", R.drawable.green_blue, 4),
                    TempDragBean.childData("蓝色3", R.drawable.green_blue, 4),
                    TempDragBean.childData("蓝色4", R.drawable.green_blue, 4),
                )),
        )

        //Gson <==> ArrayList<T> 转换器
        val convert = BaseListConvert<TempDragBean.childData>()

        //循环当前数据 【插入到数据库】
        dragTestBeanList.forEach {
            val tempList = ArrayList<childData>()

            it.child.forEach {
                tempList.add(childData(it.title, it.drawable, it.parentId))
            }

            //插入数据库
            appDataBase.getDao().instance(DragBean(it.title,
                it.isAdd,
                it.isAddVisibility,
                convert.converter(tempList)))
        }
        dragTestBeanList
    }
}

