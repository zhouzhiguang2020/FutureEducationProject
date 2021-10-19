package com.example.testproject.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.example.testproject.R;
import com.example.testproject.bean.OffSet;

/**
 * @ClassName: PhotoView2
 * @Author: android 超级兵
 * @CreateDate: 10/13/21$ 7:38 PM$
 * TODO java 版本PhotoView
 */
public class PhotoView2 extends View {

    // 双击操作
    private final GestureDetector mPhotoGestureListener;
    // 惯性滑动
    private final OverScroller mOverScroller;
    // 辅助惯性滑动类
    private final FlingRunner mFlingRunner;
    // 双指操作
    private final ScaleGestureDetector scaleGestureDetector;

    // 需要操作的图片
    private final Bitmap mBitMap;

    // 画笔
    Paint mPaint = new Paint();

    // 将图片移动到View中心
    float offsetWidth = 0f;
    float offsetHeight = 0f;

    // 缩放前图片比例
    float smallScale = 0f;

    // 缩放后图片
    float bigScale = 0f;

    // 当前比例
    float currentScale = 0f;

    // 缩放倍数
    private static final float ZOOM_SCALE = 1.5f;

    // 放大后手指移动位置
    private final OffSet moveOffset = new OffSet(0f, 0f);

    public PhotoView2(Context context) {
        this(context, null);
    }


    public PhotoView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public PhotoView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhotoView);

        Drawable drawable = typedArray.getDrawable(R.styleable.PhotoView_android_src);
        if (drawable == null)
            mBitMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.error);
        else
            mBitMap = toBitMap(drawable, 800, 800);

        // 回收 避免内存泄漏
        typedArray.recycle();

        // 双击操作
        mPhotoGestureListener = new GestureDetector(context, new PhotoGestureListener());

        //  惯性滑动
        mOverScroller = new OverScroller(context);

        // 惯性滑动辅助类
        mFlingRunner = new FlingRunner();

        // 双指头操作
        scaleGestureDetector = new ScaleGestureDetector(context, new PhotoDoubleScaleGestureListener());


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 双指操作
        boolean scaleTouchEvent = scaleGestureDetector.onTouchEvent(event);

        // 是否是双指操作
        if (scaleGestureDetector.isInProgress()) {

            return scaleTouchEvent;
        }

        // 双击操作
        return mPhotoGestureListener.onTouchEvent(event);
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/15/21 2:00 PM
     * TODO onMeasure() -> onSizeChanged() -> onDraw()
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        offsetWidth = getWidth() / 2f - mBitMap.getWidth() / 2f;
        offsetHeight = getHeight() / 2f - mBitMap.getHeight() / 2f;

        // view比例
        float viewScale = (float) getWidth() / (float) getHeight();

        // 图片比例
        float bitScale = (float) mBitMap.getWidth() / (float) mBitMap.getHeight();

        Log.i("szj图片初始化大小1", "宽:" + (float) mBitMap.getWidth() + "\t高:" + (float) mBitMap.getHeight());


        // 如果图片比例大于view比例
        if (bitScale > viewScale) {
            // 横向图片
            smallScale = (float) getWidth() / (float) mBitMap.getWidth();
            bigScale = (float) getHeight() / (float) mBitMap.getHeight() * ZOOM_SCALE;
        } else {
            // 纵向图片
            smallScale = (float) getHeight() / (float) mBitMap.getHeight();
            bigScale = (float) getWidth() / (float) mBitMap.getWidth() * ZOOM_SCALE;
        }

        Log.i("szj图片初始化大小4", "宽:" + (float) mBitMap.getWidth() * bigScale + "\t高:" + +(float) mBitMap.getHeight() * bigScale);

        Log.i("szjScale为1", "smallScale:" + smallScale + "\tbigScale:" + bigScale);


        // 当前缩放比例
        currentScale = smallScale;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * 作者:android 超级兵
         * 创建时间: 10/15/21 5:17 PM
         * TODO 平移画布
         *    	参数一:x 轴平移距离
         * 	    参数二:y 轴平移距离
         */
        float a = (currentScale - smallScale) / (bigScale - smallScale);
        // smallScale = 1.35
        // bigScale = 3.9375
        // 放大 currentScale = 1.35 -> 3.9375
        // 缩小 currentScale = 3.9375 -> 1.35
        canvas.translate(moveOffset.getX() * a, moveOffset.getY() * a);


        /*
         * 作者:android 超级兵
         * 创建时间: 10/15/21 2:33 PM
         * TODO
         *  参数一: x 缩放比例
         *  参数二: y 缩放比例
         *  参数三: x 轴位置
         *  参数四: y 轴位置
         */
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);

        canvas.drawBitmap(mBitMap, offsetWidth, offsetHeight, mPaint);
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/13/21 7:45 PM
     * TODO Drawable -> BitMap
     */
    private Bitmap toBitMap(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    //region 双指操作
    /*
     * 作者:android 超级兵
     * 创建时间: 10/19/21 12:30 PM
     */

    class PhotoDoubleScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        // 在双指操作开始时候获取当前缩放值
        private float scaleFactor = 0f;


        // 双指操作
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // detector.getScaleFactor 缩放因子
            currentScale = scaleFactor * detector.getScaleFactor();

            // 刷新
            invalidate();
            return false;
        }

        // 双指操作开始
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            scaleFactor = currentScale;
            return true;
        }

        // 双指操作结束
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            // 当前图片宽
            float currentWidth = mBitMap.getWidth() * currentScale;
            // 缩放前的图片宽
            float smallWidth = mBitMap.getWidth() * smallScale;
            // 缩放后的图片宽
            float bigWidth = mBitMap.getWidth() * bigScale;

            // 如果当前图片 < 缩放前的图片
            if (currentWidth < smallWidth) {
                // 图片缩小
                isDoubleClick = false;

                scaleAnimation(currentScale, smallScale).start();
            } else if (currentWidth > smallWidth) {
                // 图片缩小
                isDoubleClick = false;
            }

            // 如果当前状态 > 缩放后的图片 那么就让他改变为最大的状态
            if (currentWidth > bigWidth) {

                //  双击时 图片缩小
                scaleAnimation(currentScale, bigScale).start();
                // 双击时候 图片放大
                isDoubleClick = true;
            }
        }
    }
    //endregion


    //region 双击手势监听
    /*
     * 作者:android 超级兵
     */
    // 是否双击
    boolean isDoubleClick = false;

    class PhotoGestureListener extends GestureDetector.SimpleOnGestureListener {
        // 单击情况 : 抬起[ACTION_UP]时候触发
        // 双击情况 : 第二次抬起[ACTION_POINTER_UP]时候触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "抬起了 onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        // 长按时触发 [300ms]
        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "长按了 onLongPress");
            super.onLongPress(e);
        }

        // 滑动时候触发 类似 ACTION_MOVE 事件
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i("szjPhotoGestureListener", "滑动了  onScroll");

            if (isDoubleClick) {
                Log.i("szjOnScroll", "distanceX:" + distanceX + "\tdistanceY:" + distanceY);

                moveOffset.setX(moveOffset.getX() - distanceX);
                moveOffset.setY(moveOffset.getY() - distanceY);

                // 禁止图片滑动到屏幕外面
                fixOffset();

                Log.i("szjMoveOffset", "" + moveOffset.toString());
                invalidate();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        // 滑翔/飞翔 [惯性滑动]
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i("szjPhotoGestureListener", "惯性滑动 onFling");

            Log.i("szjOnFling", "velocityX:" + velocityX + "\tvelocityY" + velocityY);

            /*
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
            mOverScroller.fling(
                    (int) moveOffset.getX(),
                    (int) moveOffset.getY(),
                    (int) velocityX,
                    (int) velocityY,
                    (int) (-(mBitMap.getWidth() * bigScale - getWidth()) / 2),
                    (int) ((mBitMap.getHeight() * bigScale - getWidth()) / 2),
                    (int) (-(mBitMap.getHeight() * bigScale - getHeight()) / 2),
                    (int) ((mBitMap.getHeight() * bigScale - getHeight()) / 2),
                    300,
                    300
            );

            mFlingRunner.run();

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        // 延时触发 [100ms] -- 常用与水波纹等效果
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            Log.i("szjPhotoGestureListener", "延时触发 onShowPress");
        }

        // 按下 这里必须返回true 因为所有事件都是由按下出发的
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // 双击 -- 第二次按下时候触发 (40ms - 300ms) [小于40ms是为了防止抖动]
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "双击了 onDoubleTap");
            isDoubleClick = !isDoubleClick;
            if (isDoubleClick) {

                Log.i("szj双击位置为1", "x:" + e.getX() + "\ty:" + e.getY());

                float currentX = e.getX() - (float) getWidth() / 2f;
                float currentY = e.getY() - (float) getHeight() / 2f;

                moveOffset.setX(currentX - currentX * bigScale);

                moveOffset.setY(currentY - currentY * bigScale);

                Log.i("szj双击位置为2", "x:" + (currentX - currentX * bigScale) + "\ty" + (currentY - currentY * bigScale));

                // 重新计算，禁止放大后出现白边
                fixOffset();
                // 放大
//                currentScale = bigScale;

                scaleAnimation(currentScale, bigScale).start();
                Log.i("szjScale为2", "smallScale:" + smallScale + "\tbigScale:" + bigScale);
            } else {
                // 缩小
//                currentScale = smallScale;
                scaleAnimation(bigScale, smallScale).start();
            }
            // 不需要刷新了,在属性动画调用setCurrentScale() 的时候已经刷新了
//            invalidate();
            return super.onDoubleTap(e);
        }

        // 双击 第二次的事件处理 DOWN MOVE UP 都会执行到这里
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "双击执行了 onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        // 单击时触发 双击时不触发
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "单击了 onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }
    }

    // 惯性滑动辅助
    class FlingRunner implements Runnable {

        @Override
        public void run() {
            // 判断当前是否是执行
            if (mOverScroller.computeScrollOffset()) {
                // 设置fling的值
                moveOffset.setX(mOverScroller.getCurrX());
                moveOffset.setY(mOverScroller.getCurrY());
                Log.i("szjFlingRunner", "X:" + mOverScroller.getCurrX() + "\tY:" + mOverScroller.getCurrY());

                // 继续执行FlingRunner.run
                postOnAnimation(this);
                // 刷新
                invalidate();
            }
        }
    }

    //endregion


    /*
     * 作者:android 超级兵
     * 创建时间: 10/16/21 1:27 PM
     * TODO 修理Offset,禁止让图片溢出屏外
     */
    public void fixOffset() {
        // 当前图片放大后的宽
        float currentWidth = mBitMap.getWidth() * bigScale;
        // 当前图片放大后的高
        float currentHeight = mBitMap.getHeight() * bigScale;

        Log.i("szj图片初始化大小3", "宽:" + currentWidth + "\t高:" + currentHeight);

        // 右侧限制
        moveOffset.setX(Math.max(moveOffset.getX(), -(currentWidth - getWidth()) / 2));

        // 左侧限制 [左侧moveOffset.getX()为负数]
        moveOffset.setX(Math.min(moveOffset.getX(), (currentWidth - getWidth()) / 2));

        // 下侧限制
        moveOffset.setY(Math.max(moveOffset.getY(), -(currentHeight - getHeight()) / 2));

        // 上侧限制 [上侧moveOffset.getY()为负数]
        moveOffset.setY(Math.min(moveOffset.getY(), (currentHeight - getHeight()) / 2));
    }

    /*
     * 作者:android 超级兵
     * 创建时间: 10/15/21 4:57 PM
     * TODO start 开始缩放位置
     *      end   结束缩放位置
     */
    public ObjectAnimator scaleAnimation(float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "currentScale", start, end);
        // 动画时间
        animator.setDuration(500);
        return animator;
    }


    // 属性动画的关键!!  内部通过反射调用set方法来赋值
    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }
}
