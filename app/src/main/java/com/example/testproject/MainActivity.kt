package com.example.testproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.activity.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * TODO 渐变文字
     */
    fun onGradualChangeClick(view: View) {
        startActivity(Intent(this, GradualChangeActivity::class.java))
    }

    /**
     * TODO 传感器
     */
    fun onSensorClick(view: View) {
        startActivity(Intent(this, SensorActivity::class.java))
    }

    /**
     * TODO Rxjava
     */
    fun onRxJavaClick(view: View) {
        startActivity(Intent(this, RxJavaActivity::class.java))
    }

    /**
     * TODO Palette
     */
    fun onPaletteClick(view: View) {
        startActivity(Intent(this, PaletteActivity::class.java))
    }

    /**
     * TODO 仿支付宝功能球拖拽
     */
    fun onDragClick(view: View) {
        startActivity(Intent(this, DragHomeActivity::class.java))
    }

    /**
     * TODO jetPack Navigation
     */
    fun onNavigationClick(view: View) {
        //测试子现场是否能跳转
        thread {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
    }

    /**
     * TODO SortedActivity
     */
    fun onSortedClick(view: View) {
        startActivity(Intent(this, SortedActivity::class.java))
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/11/21 5:30 PM
     * TODO 图片缩放
     */
    fun onPhotoClick(view: View) {
        startActivity(Intent(this, PhotoZoomActivity::class.java))
    }

}


