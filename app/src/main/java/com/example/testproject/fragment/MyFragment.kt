package com.example.testproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.testproject.R
import com.example.testproject.databinding.MyFragmentDataBinDing
import com.example.testproject.utils.toast
import com.example.testproject.view_model.TestViewModel

class MyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val viewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)

        val dataBin: MyFragmentDataBinDing =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)


        dataBin.data = viewModel
        dataBin.lifecycleOwner = activity

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            Log.i("szj输出了My", "$it")
            dataBin.btMyAdd.text = "$it"
        }


        dataBin.btMy.setOnClickListener {
            //返回上一个界面
            try {
                Navigation.findNavController(it).navigateUp()

            } catch (e: Exception) {
                "别点我.我是用来navtgation测试的".toast(it.context)
            }
        }

        dataBin.btStartMy.setOnClickListener {

            try {
                Navigation.findNavController(it).navigate(R.id.settingFragment)

            } catch (e: Exception) {
                "别点我.我是用来navtgation测试的".toast(it.context)
            }
        }

        return dataBin.root
    }


}