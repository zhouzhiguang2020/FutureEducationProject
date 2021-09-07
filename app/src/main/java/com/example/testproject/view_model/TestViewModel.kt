package com.example.testproject.view_model

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.*
import java.util.*

/**
 *
 * @Package: com.example.testproject.view_model
 * @ClassName: TestViewModel
 * @Author: szj
 * @CreateDate: 8/30/21
 */
class TestViewModel : ViewModel(), LifecycleObserver {

    private var liveData: MutableLiveData<Int>? = null

    fun getLiveData(): MutableLiveData<Int> {
        if (liveData == null) {
            liveData = MutableLiveData<Int>()
            liveData?.value = 1
        }
        return liveData as MutableLiveData<Int>
    }

    public fun add(a: Int) {
        liveData?.value = a
    }
}