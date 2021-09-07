package com.example.testproject.base

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *
 * @Package: com.example.testproject.base
 * @ClassName: DragBean
 * @Author: szj
 * @CreateDate: 9/3/21
 */
data class TempDragBean(
    var title: String, // 标题 首页常用功能
    var isAdd: Boolean, // 是否是添加
    var isAddVisibility: Boolean, //是否显示添加按钮
    var child: List<childData>, //子信息
) {
    data class childData(
        var title: String,//名字
        var drawable: Int, //图片
        var parentId: Int, //组Id
    )
}