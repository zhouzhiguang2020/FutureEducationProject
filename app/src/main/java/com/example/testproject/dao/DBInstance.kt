package com.example.testproject.dao

import android.content.Context
import androidx.room.Room

/**
 *
 * @Package: com.example.testproject.dao
 * @ClassName: DBInstance
 * @Author: szj
 * @CreateDate: 9/6/21
 */
object DBInstance {
    private const val DB_NAME = "room"
    var appDataBase: AppDataBase? = null
    fun getInstance(context: Context?): AppDataBase {
        if (appDataBase == null) {
            synchronized(DBInstance::class.java) {
                if (appDataBase == null) {
                    return Room.databaseBuilder(context!!,
                        AppDataBase::class.java,
                        DB_NAME) .allowMainThreadQueries()
                        .build()
                }
            }
        }
        return appDataBase!!
    }
}