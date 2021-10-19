package com.example.testproject.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.example.testproject.R
import com.example.testproject.bean.OffSet
import com.example.testproject.ext.toBitmap
import kotlin.properties.Delegates

/**
 *
 * @ClassName: PhotoView2
 * @Author: android 超级兵
 * @CreateDate: 10/11/21$ 5:31 PM$
 * TODO 自定义移动PhotoView
 */
class PhotoView : View {

    // 设置图片
    private var mBitMap: Bitmap

    // 图片宽
    private var bitMapWidth by Delegates.notNull<Float>()

    // 图片高
    private var bitMapHeight by Delegates.notNull<Float>()

    // 当前平移宽/高
    private var currentOffset = OffSet(0f, 0f)

    // 放大后手指移动位置
    private var moveOffset = OffSet(0f, 0f)

    // 小放大 一边全屏幕 一边留白
    private var smallScale = 0f

    // 一边全屏幕 一边超出屏幕
    private var bigScale = 0f


    // 当前缩放值
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    // 双击事件
    private var photoGestureDetector: GestureDetector

    // 惯性滑动
    private var overScroller: OverScroller

    // 重复刷新,用来设置惯性滑动
    private val flingRunner: FlingRunner

    // 双指操作[用于双指头放大缩小]
    private var scaleGestureDetector: ScaleGestureDetector

    // 是否缩放 true 放大 false 缩小
    private var isScale = false

    companion object {
        // 缩放比例
        const val ZOOM_SCALE = 1.5f

        // 画笔
        private val mPaint = Paint()
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("Recycle", "NewApi")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val obtainStyledAttributes = context?.obtainStyledAttributes(attrs, R.styleable.PhotoView)

        val drawable =
            obtainStyledAttributes!!.getDrawable(R.styleable.PhotoView_android_src)

        mBitMap = drawable?.toBitmap(800, 800)
            ?: context.getDrawable(R.drawable.ic_launcher_background)!!.toBitmap(800, 1000)

        // 回收
        obtainStyledAttributes.recycle()

        // 双击手势操作
        photoGestureDetector = GestureDetector(context, PhotoGestureListener())

        //  惯性滑动
        overScroller = OverScroller(context)

        flingRunner = FlingRunner()

        // 双指操作
        scaleGestureDetector = ScaleGestureDetector(context, PhotoScaleGestureListener())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = scaleGestureDetector.onTouchEvent(event)
        // 双指操作
        if (!scaleGestureDetector.isInProgress) {
            result = photoGestureDetector.onTouchEvent(event)
        }

        // 双击操作
        return result
    }


    /*
    * 作者:android 超级兵
    * 创建时间: 10/11/21 5:52 PM
    * TODO 执行顺序: onMeasure() -> onSizeChanged() -> onDraw()
    */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentOffset.x = width / 2f - mBitMap.width / 2f
        currentOffset.y = height / 2f - mBitMap.height / 2f

        bitMapWidth = mBitMap.width.toFloat()
        bitMapHeight = mBitMap.height.toFloat()

        // 当前缩放比例 > 图片比例 那么证明是横向图片
        if (bitMapWidth / bitMapHeight > width.toFloat() / height.toFloat()) {
            // 横向图片
            smallScale = width / mBitMap.width.toFloat()
            bigScale = height / mBitMap.height.toFloat() * ZOOM_SCALE
        } else {
            //纵向图片
            smallScale = height / mBitMap.height.toFloat()
            bigScale = width / mBitMap.width.toFloat() * ZOOM_SCALE
        }

        Log.i("szj最大/小值", "smallScale:${smallScale}\tbigScale${bigScale}}")

        // 当前缩放
        currentScale = smallScale
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i("szjCurrentScale", "currentScale:${currentScale} == bigScale:${bigScale}")

        Log.i("szj平移", moveOffset.toString())
        /*
         * 作者:android 超级兵
         * 创建时间: 10/11/21 8:51 PM
         * TODO 平移
         *  越往左 moveOffset.x 越小
         *  越往下 moveOffset.y 越小
         */
        val a = (currentScale - smallScale) / (bigScale - smallScale)

        Log.i("szj缩放系数为:", "$a")
        canvas.translate(moveOffset.x * a, moveOffset.y * a)

        /*
         * 作者:android 超级兵
         * 创建时间: 10/11/21 7:12 PM
         * TODO 缩放画布
         *  参数一: X 缩放比例
         *  参数二: Y 缩放比例
         *  参数三: px 缩放X位置
         *  参数四: pY 缩放Y位置
         */
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        Log.i("szjCurrentScale为:", "$${width / 2f}\theight:${height / 2f}")
        /*
         * 作者:android 超级兵
         * 创建时间: 10/11/21 7:13 PM
         * TODO 绘制图片
         *  参数一: 图片[bitMap]
         *  参数二:
         */
        canvas.drawBitmap(mBitMap, currentOffset.x, currentOffset.y, mPaint)
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/11/21 7:26 PM
     * TODO 双击手势监听
     */
    inner class PhotoGestureListener : GestureDetector.SimpleOnGestureListener() {

        // 延时触发 [100ms] -- 常用与水波纹等效果
        override fun onShowPress(e: MotionEvent?) {
            super.onShowPress(e)
            Log.i("szjPhotoGestureListener", "延时触发 onShowPress")
        }

        // 单击情况 : 抬起[ACTION_UP]时候触发
        // 双击情况 : 第二次抬起[ACTION_POINTER_UP]时候触发
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Log.i("szjPhotoGestureListener", "抬起了 onSingleTapUp")
            return super.onSingleTapUp(e)
        }

        // 滑动时候触发 类似 ACTION_MOVE 事件
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {
            Log.i("szjPhotoGestureListener", "滑动了  onScroll")


            // distanceX
            //      向左滑动 正数
            //      向右滑动 负数
            // distanceY
            //      向上滑动 正数
            //      向下滑动 负数
            Log.i("szjOnScroll", "distanceX:${distanceX}\tdistanceY:${distanceY}")

            // 如果是放大情况 则可以移动
//            if (isScale) {
            moveOffset.x -= distanceX
            moveOffset.y -= distanceY

            // 滑动时候 处理偏移量,不让超出区间
            fixOffset()

            invalidate()
//            }

            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        // 长按时触发 [300ms]
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            Log.i("szjPhotoGestureListener", "长按了 onLongPress")
        }

        // 按下 这里必须返回true 因为所有事件都是由按下出发的
        override fun onDown(e: MotionEvent?): Boolean {
//            return super.onDown(e)
            return true
        }

        // 滑翔/飞翔 [惯性滑动]
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            Log.i("szjPhotoGestureListener", "惯性滑动 onFling")

            // 只会处理一次

//            if (isScale) {
            /*
             * 作者:android 超级兵
             * 创建时间: 10/12/21 4:47 PM
             * TODO
             * int startX       滑动x
             * int startY,      滑动y
             * int velocityX,   每秒像素为单位[x轴]
             * int velocityY,   每秒像素为单位[y轴]
             * int minX,        宽最小值
             * int maxX,        宽最大值
             * int minY,        高最小值
             * int maxY,        高最大值
             * int overX,       溢出x的距离
             * int overY        溢出y的距离
             */
            overScroller.fling(
                moveOffset.x.toInt(),
                moveOffset.y.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                (-(bitMapWidth * bigScale - width) / 2).toInt(),
                ((bitMapWidth * bigScale - width) / 2).toInt(),
                (-(bitMapHeight * bigScale - height) / 2).toInt(),
                ((bitMapHeight * bigScale - height) / 2).toInt(),
                300,
                300
            )

            flingRunner.run()
//            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }


        // 单击时触发 双击时不触发
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            Log.i("szjPhotoGestureListener", "单击了 onSingleTapConfirmed")
            return super.onSingleTapConfirmed(e)
        }

        // 双击 -- 第二次按下时候触发 (40ms - 300ms) [小于40ms是为了防止抖动]
        override fun onDoubleTap(e: MotionEvent): Boolean {
            isScale = !isScale

            if (isScale) {
                moveOffset.x = (e.x - width / 2f) - ((e.x - width / 2f) * bigScale) / smallScale
                moveOffset.y = (e.y - height / 2f) - ((e.y - height / 2f) * bigScale) / smallScale

                fixOffset()

                // 放大
//                currentScale = bigScale
                scaleAnimation(currentScale, bigScale).start()
//                scaleAnimation().start()
            } else {
                // 缩小
//                currentScale = smallScale
                scaleAnimation(bigScale, smallScale).start()
//                scaleAnimation().reverse()
            }

//            invalidate()
            Log.i("szjPhotoGestureListener", "双击了 onDoubleTap")

            return super.onDoubleTap(e)
        }

        // 双击 第二次的事件处理 DOWN MOVE UP 都会执行到这里
        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            Log.i("szjPhotoGestureListener", "双击执行了 onDoubleTapEvent")
            return super.onDoubleTapEvent(e)
        }
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/12/21 5:03 PM
     * TODO 两指头操作[放大缩小]
     */
    inner class PhotoScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        private var scaleFactor = 0f

        override fun onScale(detector: ScaleGestureDetector) = let {
            Log.i("szj缩放因子11", "$currentScale\t${detector.scaleFactor}")
            // detector!!.scaleFactor 缩放因子
            currentScale = scaleFactor * detector.scaleFactor

            invalidate()
            false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?) = let {
            scaleFactor = currentScale
            // 返回true表示消费事件
            true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            // 当前图片宽
            val currentWidth = bitMapWidth * currentScale
            // 缩放前的图片宽
            val smallWidth = bitMapWidth * smallScale
            // 缩放后的图片宽
            val bigWidth = bitMapWidth * bigScale

            // 如果当前图片 < 缩放前的图片
            if (currentWidth < smallWidth) {
                // 图片缩小
                isScale = false
                scaleAnimation(currentScale, smallScale).start()
            } else if (currentWidth > smallWidth) {
                // 图片缩小
                isScale = false
            }

            // 如果当前状态 > 缩放后的图片 那么就让他改变为最大的状态
            if (currentWidth > bigWidth) {

                //  双击时 图片缩小
                scaleAnimation(currentScale, bigScale).start()
                // 双击时候 图片放大
                isScale = true
            }
        }
    }

    inner class FlingRunner : Runnable {
        override fun run() {
            // 判断当前是否在执行
            if (overScroller.computeScrollOffset()) {
                // 反复改变xy
                moveOffset.x = overScroller.currX.toFloat()
                moveOffset.y = overScroller.currY.toFloat()

                // 反复执行
                postOnAnimation(this)

                invalidate()
            }
        }
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/11/21 8:10 PM
     * TODO 移动Offset 不让图片移动出边界
     */
    fun fixOffset() {
        // 当前图片放大后宽度
        val currentWidth = bitMapWidth * bigScale
        // 当前图片方法后高度
        val currentHeight = bitMapHeight * bigScale

        Log.i("szjMoveIffset是多少", moveOffset.toString())
        Log.i("szjCurrent是多少", "${(currentWidth - width) / 2}\t 取反:${-(currentWidth - width) / 2}")

        moveOffset.x = Math.max(moveOffset.x, -(currentWidth - width) / 2)
        moveOffset.x = Math.min(moveOffset.x, (currentWidth - width) / 2)

        moveOffset.y = Math.max(moveOffset.y, -(currentHeight - height) / 2)
        moveOffset.y = Math.min(moveOffset.y, (currentHeight - height) / 2)
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/11/21 7:39 PM
     * TODO 设置属性动画
     */
    fun scaleAnimation(start: Float = smallScale, end: Float = bigScale): ObjectAnimator = let {
        ObjectAnimator.ofFloat(this, "currentScale", start, end).also {
            // 默认时间是 300ms
            it.duration = 500

        }
    }
}