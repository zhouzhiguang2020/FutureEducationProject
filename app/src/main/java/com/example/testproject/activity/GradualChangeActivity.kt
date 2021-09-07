package com.example.testproject.activity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.testproject.R
import com.example.testproject.adapter.ViewPagerAdapter
import com.example.testproject.databinding.GradualChangeActivityDataBinDing
import com.example.testproject.databinding.GradualChangeDataBinDing
import com.example.testproject.fragment.HomeFragment
import com.example.testproject.fragment.MyFragment
import com.example.testproject.fragment.SettingFragment
import com.example.testproject.fragment.TestFragment
import com.example.testproject.utils.gradual_change.GradualChangeTextView

/**
 *
 * @Package: com.example.testproject
 * @ClassName: GradualChangeActivity
 * @Author: szj
 * @CreateDate: 8/9/21 1:02 PM
 * TODO 渐变文字页面
 * TODO 博客地址:https://blog.csdn.net/weixin_44819566/article/details/119604710
 * TODO gitee下载地址:https://gitee.com/lanyangyangzzz/android_ui
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
        val db: GradualChangeActivityDataBinDing = DataBindingUtil.setContentView(
            this,
            R.layout.activity_gradual_change_debug
        )

        db.javaLeft.setOnClickListener { db.javaGradualTv.setType(GradualChangeTextView.GRADUAL_CHANGE_LEFT) }

        db.javaRight.setOnClickListener { db.javaGradualTv.setType(GradualChangeTextView.GRADUAL_CHANGE_RIGHT) }


    }

    /**
     * TODO kotlin =============================================================
     */
    private fun initKotlin() {
        val db: GradualChangeDataBinDing = DataBindingUtil.setContentView(
            this,
            R.layout.activity_gradual_change
        )


        //从左到右滑动
        db.left.setOnClickListener {
            db.changeTextView.setSlidingPosition(GradualChangeTextView.GRADUAL_CHANGE_LEFT)
            startAnimator(db)
        }

        //从右到左滑动
        db.right.setOnClickListener {
            db.changeTextView.setSlidingPosition(GradualChangeTextView.GRADUAL_CHANGE_RIGHT)
            startAnimator(db)
        }


        // 初始化数据
        val list = ArrayList<View>();


        repeat(10) {
            val img = ImageView(this)
            img.setBackgroundColor(Color.parseColor("#aabbcc"))
            list.add(img)
        }


        initViewPager(db)
    }

    private fun initViewPager(db: GradualChangeDataBinDing) {
        //text1 .. text4 是控件id
        val textList = listOf(db.text1, db.text2, db.text3, db.text4)

        val list = listOf(HomeFragment(), MyFragment(), TestFragment(), SettingFragment())

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, list)

        db.viewPager.adapter = viewPagerAdapter

        //默认选择第一页
        db.viewPager.currentItem = 1

        //默认选中
        textList[db.viewPager.currentItem].percent = 1f

        db.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
                    if (it.tag == textList[db.viewPager.currentItem].tag) {
                        it.percent = 1f
                    } else {
                        it.percent = 0f
                    }
                }
            }
        })

        //点击事件处理
        repeat(db.linear.childCount) {
            val view = db.linear.getChildAt(it)
            view.tag = it
            view.setOnClickListener {
                db.viewPager.currentItem = view.tag as Int
            }
        }
    }


    /**
     * time 动画时间
     */
    private fun startAnimator(db: GradualChangeDataBinDing, time: Long = 3000) {
        ObjectAnimator.ofFloat(db.changeTextView, "percent", 0f, 1f).apply {
            duration = time
            start()
        }
    }
}