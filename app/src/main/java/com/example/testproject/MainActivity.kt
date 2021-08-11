package com.example.testproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.testproject.activity.GradualChangeActivity
import java.io.*

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

}