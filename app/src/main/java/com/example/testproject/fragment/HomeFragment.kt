package com.example.testproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.testproject.R
import com.example.testproject.databinding.HomeFragmentDataBinDing
import com.example.testproject.utils.toast
import com.example.testproject.view_model.TestViewModel

class HomeFragment : Fragment() {

    var i = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val viewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)

        val dataBinDing: HomeFragmentDataBinDing =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        //绑定数据
        dataBinDing.data = viewModel
        dataBinDing.lifecycleOwner = requireActivity()


        dataBinDing.btHomeAdd.setOnClickListener {
           viewModel.add(i++)
        }

        dataBinDing.btHome.setOnClickListener {
            try {
                Navigation.findNavController(it).navigate(R.id.myFragment)
            } catch (e: Exception) {
                "别点我.我是用来navtgation测试的".toast(it.context)
            }
        }
        return dataBinDing.root
    }


}