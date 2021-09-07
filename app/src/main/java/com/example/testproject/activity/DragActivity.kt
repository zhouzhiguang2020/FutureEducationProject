package com.example.testproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testproject.R
import com.example.testproject.databinding.DragDataBinDing

/**
 *
 * @Package: com.example.testproject.activity
 * @ClassName: DragActivity
 * @Author: szj
 * @CreateDate: 8/26/21 9:59 AM
 * TODO 拖拽 详情
 */
class DragActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db: DragDataBinDing = DataBindingUtil.setContentView(this, R.layout.fragment_drag_details)

    }

}