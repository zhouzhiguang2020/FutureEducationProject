package com.example.testproject.activity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.R
import kotlinx.android.synthetic.main.activity_sensor.*
import kotlin.math.abs

/**
 *
 * @Package: com.example.testproject.activity
 * @ClassName: SensorActivity
 * @Author: szj
 * @CreateDate: 8/18/21 8:49 PM
 * TODO 传感器
 */
class SensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        //TODO 获取传感器的服务
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 注册水平仪监听
//        sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)?.also { accelerometer ->
//            sensorManager.registerListener(
//                this,
//                accelerometer,
//                SensorManager.SENSOR_DELAY_NORMAL,
//                SensorManager.SENSOR_DELAY_UI
//            )
//        }

        // 为系统的方向传感器注册监听器
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME);
    }


    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        when (event.sensor.type) {
            Sensor.TYPE_ORIENTATION -> {
                val x = values[0]
                var y = abs(values[1]) / 180 * 30
                val z = abs(values[2]) / 180 * 30
//                y = (y / 180) * 50
                Log.i("szj传感器", "x:${x}\ty:${y}\tz:${z}")
                tvSensor.text = "x:${x}\ny:${y}\nz:${z}"

                imageSensor.scrollTo(z.toInt(), y.toInt())
                imageSensor2.scrollTo(z.toInt(), y.toInt())
                imageSensor3.scrollTo(-z.toInt(), -y.toInt())
            }
            else -> {
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onDestroy() {
        //取消注册
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }

}