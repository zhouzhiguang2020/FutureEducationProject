package com.example.testproject.dao

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
 * @Package: com.example.testproject.dao
 * @ClassName: AppDataBase
 * @Author: szj
 * @CreateDate: 9/6/21
 */
@Database(entities = [DragBean::class, childData::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): DragDao
}
