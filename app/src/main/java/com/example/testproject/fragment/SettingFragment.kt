package com.example.testproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.testproject.R
import com.example.testproject.utils.toast
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btSetting.setOnClickListener {
            try {
                Navigation.findNavController(view).navigateUp()
            } catch (e: Exception) {
                "别点我.我是用来navtgation测试的".toast(view.context)
            }
        }

    }


}