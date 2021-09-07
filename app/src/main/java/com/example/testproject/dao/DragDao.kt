package com.example.testproject.dao

import androidx.room.*

/**
 *
 * @Package: com.example.testproject.dao
 * @ClassName: DragDao
 * @Author: szj
 * @CreateDate: 9/6/21
 */
@Dao
interface DragDao {
    //增
    @Insert
    fun instance(vararg dragDao: DragBean)

    //删
    @Delete
    fun delete(dragDao: DragBean)

    //改
    @Update
    fun upData(dragDao: DragBean)

    //查询
    @Query("select * from DragBean")
    fun getAll(): List<DragBean>
}