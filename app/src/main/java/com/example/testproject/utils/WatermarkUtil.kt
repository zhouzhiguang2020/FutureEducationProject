package com.example.testproject.utils

import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.annotation.IntDef


/**
 *
 * @ClassName: WatermarkUtil
 * @Author: android 超级兵
 * @CreateDate: 10/19/21$ 9:40 AM$
 * TODO 图片添加水印
 */
class WatermarkUtil {

    companion object {
        val instance by lazy {
            WatermarkUtil()
        }

        const val LEFT_CENTER = 0   // 左中


        const val LEFT_TOP = 1    // 左上
        const val LEFT_BOTTOM = 2 // 左下

        const val RIGHT_CENTER = 3 // 右中
        const val RIGHT_TOP = 4 // 右上
        const val RIGHT_BOTTOM = 5  // 右下

        const val TOP_CENTER = 6 // 上中
        const val TOP_LEFT = 7// 上左
        const val TOP_RIGHT = 8// 上右

        const val BOTTOM_CENTER = 9// 下中
        const val BOTTOM_LEFT = 10// 下左
        const val BOTTOM_RIGHT = 11// 下右

        const val CENTER_CENTER = 12// 居中
    }


    /*
     * 作者:android 超级兵
     * 创建时间: 10/19/21 9:43 AM
     * TODO 绘制文字到图片上
     *  参数一: context
     *  参数二: 要设置的图片
     *  参数三: 文字绘制图片的位置
     *  参数四: 文字的基本信息
     *  参数五: 文字具体图片的位置
     */
     fun drawTextToBitMap(
        context: Context,
        bitmap: Bitmap,
        @WatermarkUtilType
        type: Int = LEFT_CENTER,
        textBean: TextBean
    ) = let {
        val paint = Paint()
        paint.color = textBean.textColor
        paint.textSize = context.dp2px(textBean.textSize)
        paint.isDither = true // 获取跟清晰的图像采样
        paint.isFilterBitmap = true // 过滤一些

        val bounds = Rect()
        paint.getTextBounds(textBean.text, 0, textBean.text.length, bounds)


        val bitCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        val canvas = Canvas(bitCopy)

        val pair = retrievalPosition(type, bitCopy, bounds)

        Log.i("szjPair", "first:${pair.first}\tsecond${pair.second}")
        canvas.drawText(textBean.text,
            pair.first,
            pair.second,
            paint)
        bitCopy
    }!!

    /*
     * 作者:android 超级兵
     * 创建时间: 10/19/21 10:28 AM
     * TODO  检索位置
     */
    private fun retrievalPosition(type: Int, bitMap: Bitmap, bounds: Rect) = let {

        var pair = Pair(0f, 0f)

        Log.i("szjBitMap", "width:${bitMap.width}\theight${bitMap.height}")
        when (type) {
            LEFT_CENTER -> {
                pair = Pair(0f, bitMap.height / 2f + bounds.height() / 2)
            }
            TOP_LEFT,
            LEFT_TOP,
            -> {
                pair = Pair(0f, bounds.height().toFloat())
            }
            BOTTOM_LEFT,
            LEFT_BOTTOM,
            -> {
                pair = Pair(0f, bitMap.height.toFloat() - bounds.height() / 2)
            }

            RIGHT_CENTER -> {
                pair = Pair(bitMap.width.toFloat() - bounds.width(),
                    bitMap.height / 2f + bounds.height() / 2)
            }
            TOP_RIGHT,
            RIGHT_TOP,
            -> {
                pair = Pair(bitMap.width.toFloat() - bounds.width(), bounds.height().toFloat())
            }
            BOTTOM_RIGHT,
            RIGHT_BOTTOM,
            -> {
                pair = Pair(bitMap.width.toFloat() - bounds.width(),
                    bitMap.height.toFloat() - bounds.height() / 2)
            }

            TOP_CENTER -> {
                pair = Pair(bitMap.width / 2f - bounds.width() / 2, bounds.height().toFloat())
            }

            BOTTOM_CENTER -> {
                pair = Pair(bitMap.width / 2f - bounds.width() / 2,
                    bitMap.height.toFloat() - bounds.height() / 2)
            }
            CENTER_CENTER->{
                pair = Pair(bitMap.width / 2f - bounds.width() / 2,
                    bitMap.height / 2f + bounds.height() / 2)
            }
        }
        pair
    }


}

/*
* 作者:android 超级兵
* 创建时间: 10/19/21 10:04 AM
* TODO  dip转pix
*/
fun Context.dp2px(dp: Float) = let {
    val scale: Float = resources.displayMetrics.density
    dp * scale + 0.5f
}

data class TextBean(
    val text: String = "设置文字呀笨蛋。。",
    val textColor: Int = Color.RED,
    val textSize: Float = 50f,
)

/*
 * 作者:android 超级兵
 * 创建时间: 10/19/21 9:59 AM
 * TODO  自定义注解,代替 enum class
 */
@IntDef(WatermarkUtil.LEFT_CENTER,
    WatermarkUtil.LEFT_TOP,
    WatermarkUtil.LEFT_BOTTOM,
    WatermarkUtil.RIGHT_CENTER,
    WatermarkUtil.RIGHT_TOP,
    WatermarkUtil.RIGHT_BOTTOM,
    WatermarkUtil.TOP_CENTER,
    WatermarkUtil.TOP_LEFT,
    WatermarkUtil.TOP_RIGHT,
    WatermarkUtil.BOTTOM_CENTER,
    WatermarkUtil.BOTTOM_LEFT,
    WatermarkUtil.BOTTOM_RIGHT,
    WatermarkUtil.CENTER_CENTER
)
annotation class WatermarkUtilType


