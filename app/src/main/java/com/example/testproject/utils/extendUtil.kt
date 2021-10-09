package com.example.testproject.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 *
 * @Package: com.example.testproject.utils
 * @ClassName: extendUtil
 * @Author: szj
 * @CreateDate: 9/3/21
 */

//TODO Toast
@SuppressLint("ShowToast")
infix fun <T> T.toast(context: Context) {
    Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()
}



//TODO 利用RxView机制 防止View重复点击
fun View.click(
    time: Int = 2, // 时间[单位:秒(s)]
    click: () -> Unit,
) {
    RxView.clicks(this).throttleFirst(time.toLong(), TimeUnit.SECONDS)
        .subscribe(object : io.reactivex.Observer<Any> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(value: Any) {
                click()
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }
        })
}