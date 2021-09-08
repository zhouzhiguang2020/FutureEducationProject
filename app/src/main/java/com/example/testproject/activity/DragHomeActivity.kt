package com.example.testproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testproject.R
import com.example.testproject.databinding.DragHomeBinDing
import com.example.testproject.databinding.DragHomeDataBinDing

/**
 *
 * @Package: com.example.testproject.activity
 * @ClassName: DragHomeActivity
 * @Author: szj
 * @CreateDate: 9/3/21
 * TODO 拖拽首页
 * TODO 原创地址:https://blog.csdn.net/weixin_44819566/article/details/120158278
 */
class DragHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val da: DragHomeDataBinDing = DataBindingUtil.setContentView(this, R.layout.activity_drag_home)
    }
}