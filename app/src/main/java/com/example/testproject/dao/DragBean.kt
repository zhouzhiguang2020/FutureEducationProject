package com.example.testproject.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @Package: com.example.testproject.dao
 * @ClassName: DragBean
 * @Author: szj
 * @CreateDate: 9/6/21
 */

@Entity
//@TypeConverters(RoomListConvert::class)
class DragBean {
    //主键自增
    @PrimaryKey(autoGenerate = true)
    public var id = 0

    @ColumnInfo
    public var title // 标题 首页常用功能
            : String? = null

    @ColumnInfo
    public var isAdd: Boolean? = null// 是否是添加

    @ColumnInfo
    public var isAddVisibility: Boolean? = null//是否显示添加按钮

    @ColumnInfo
    public var child: String? = null //子信息

    constructor()

    constructor(title: String?, isAdd: Boolean?, isAddVisibility: Boolean?, list: String?) {
        this.title = title
        this.isAdd = isAdd
        this.isAddVisibility = isAddVisibility
        child = list
    }


    override fun toString(): String {
        return "DragBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isAdd=" + isAdd +
                ", isAddVisibility=" + isAddVisibility +
                ", child=" + child +
                '}'
    }



}