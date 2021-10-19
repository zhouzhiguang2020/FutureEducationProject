package com.example.testproject.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable

/**
 *
 * @ClassName: BitMapExt
 * @Author: android 超级兵
 * @CreateDate: 10/11/21$ 5:54 PM$
 * TODO Drawable 和 BitMap 扩展
 */


/*
 * 作者:android 超级兵
 * 创建时间: 10/11/21 5:54 PM
 * TODO Drawable -> BitMap
 */
fun Drawable.toBitmap(width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)
    return bitmap
}