package com.example.testproject.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class childData {
    @PrimaryKey(autoGenerate = true)
    public var id = 0

    @ColumnInfo
     var childTitle //名字'
            : String? = null

    @ColumnInfo
    public var childDrawable //图片
            = 0

    @ColumnInfo
    public var childParentId //组Id
            = 0

    override fun toString(): String {
        return "childData{" +
                "id=" + id +
                ", childTitle='" + childTitle + '\'' +
                ", childDrawable=" + childDrawable +
                ", childParentId=" + childParentId +
                '}'
    }

    constructor()

    //        @Ignore
    constructor(title: String, drawable: Int, parentId: Int) {
        childTitle = title
        childDrawable = drawable
        childParentId = parentId
    }
}