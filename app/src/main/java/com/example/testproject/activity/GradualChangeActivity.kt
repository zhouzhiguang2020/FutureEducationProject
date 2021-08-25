package com.example.testproject.activity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.testproject.R
import com.example.testproject.adapter.ViewPagerAdapter
import com.example.testproject.fragment.HomeFragment
import com.example.testproject.fragment.MyFragment
import com.example.testproject.fragment.SettingFragment
import com.example.testproject.fragment.TestFragment
import com.example.testproject.utils.gradual_change.GradualChangeTextView
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_gradual_change.*
import kotlinx.android.synthetic.main.activity_gradual_change_debug.*
import java.util.concurrent.TimeUnit

/**
 *
 * @Package: com.example.testproject
 * @ClassName: GradualChangeActivity
 * @Author: szj
 * @CreateDate: 8/9/21 1:02 PM
 * TODO 渐变文字页面
 * TODO 博客地址:https://blog.csdn.net/weixin_44819566/article/details/119604710
 * TODO gitee下载地址:
 */
class GradualChangeActivity : AppCompatActivity() {

    private val isJava = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "渐变文字Demo"
        if (isJava) {
            initJava()
        } else {
            initKotlin()
        }
    }

    /**
     * TODO java =============================================================
     */
    private fun initJava() {
        setContentView(R.layout.activity_gradual_change_debug)

        javaLeft.setOnClickListener { java_gradual_tv.setType(GradualChangeTextView.GRADUAL_CHANGE_LEFT) }

        javaRight.setOnClickListener { java_gradual_tv.setType(GradualChangeTextView.GRADUAL_CHANGE_RIGHT) }


    }

    /**
     * TODO kotlin =============================================================
     */
    private fun initKotlin() {
        setContentView(R.layout.activity_gradual_change)

        //从左到右滑动
        left.setOnClickListener {
            change_text_view.setSlidingPosition(GradualChangeTextView.GRADUAL_CHANGE_LEFT)
            startAnimator()
        }

        //从右到左滑动
        right.setOnClickListener {
            change_text_view.setSlidingPosition(GradualChangeTextView.GRADUAL_CHANGE_RIGHT)
            startAnimator()
        }


        // 初始化数据
        val list = ArrayList<View>();


        repeat(10) {
            val img = ImageView(this)
            img.setBackgroundColor(Color.parseColor("#aabbcc"))
            list.add(img)
        }


        initViewPager()
    }

    private fun initViewPager() {
        //text1 .. text4 是控件id
        val textList = listOf(text1, text2, text3, text4)

        val list = listOf(HomeFragment(), MyFragment(), TestFragment(), SettingFragment())

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, list)

        viewPager.adapter = viewPagerAdapter

        //默认选择第一页
        viewPager.currentItem = 1

        //默认选中
        textList[viewPager.currentItem].percent = 1f

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                if (positionOffset > 0) {
                    val left = textList[position]
                    val right = textList[position + 1]

                    left.setSlidingPosition(GradualChangeTextView.GRADUAL_CHANGE_RIGHT)
                    right.setSlidingPosition(GradualChangeTextView.GRADUAL_CHANGE_LEFT)

                    left.percent = 1 - positionOffset
                    right.percent = positionOffset
                }
                Log.i("szjViewPager", "$position positionOffset:${positionOffset}")
            }

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                //当 ViewPage结束的时候,重新设置一下状态 [不设置的话会有'残影']
                textList.forEach {
                    if (it.tag == textList[viewPager.currentItem].tag) {
                        it.percent = 1f
                    } else {
                        it.percent = 0f
                    }
                }
            }
        })

        //点击事件处理
        repeat(linear.childCount) {
            val view = linear.getChildAt(it)
            view.tag = it
            view.setOnClickListener {
                viewPager.currentItem = view.tag as Int
            }
        }
    }


    /**
     * time 动画时间
     */
    private fun startAnimator(time: Long = 3000) {
        ObjectAnimator.ofFloat(change_text_view, "percent", 0f, 1f).apply {
            duration = time
            start()
        }
    }
}