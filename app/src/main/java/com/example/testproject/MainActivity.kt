package com.example.testproject

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.activity.GradualChangeActivity
import com.example.testproject.activity.PaletteActivity
import com.example.testproject.activity.RxJavaActivity
import com.example.testproject.activity.SensorActivity
import kotlinx.android.synthetic.main.activity_main.*


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
     * TODO 传感器
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


}


