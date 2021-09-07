package com.example.testproject.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.testproject.R
import com.example.testproject.adapter.DragAdapter
import com.example.testproject.adapter.DragChildAdapter
import com.example.testproject.base.TempDragBean
import com.example.testproject.databinding.DragDataBinDing
import com.example.testproject.template.DragTemplate
import com.example.testproject.utils.toast
import com.example.testproject.view_model.DragViewModel

/**
 *
 * @Package: com.example.testproject.fragment
 * @ClassName: DragDetailsFragment
 * @Author: szj
 * @CreateDate: 9/6/21
 */
class DragDetailsFragment : Fragment() {

    private lateinit var dataBinDing: DragDataBinDing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinDing =
            DataBindingUtil.inflate(inflater, R.layout.fragment_drag_details, container, false)
        return dataBinDing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(DragViewModel::class.java)
        viewModel.isHome = false

        //记录刚进来第一次的数据
        val recordFirstData = viewModel.getData(requireContext())

        //RecyclerView初始化
        val template = DragTemplate(viewModel.getData(requireContext()))
        template.template(requireContext(), dataBinDing.recyclerView)
        val dragAdapter = template.getAdapter() as DragAdapter


        //退出监听
        dataBinDing.btQuit.setOnClickListener {
            //适配器更新状态 [点击退出的时候,状态改变为刚进入的时候]
            editData(dragAdapter)

            // 更新数据
            //  dragAdapter.setNewData(recordFirstData)

            // 数据库中也跟着更新
            viewModel.upData(requireContext(), recordFirstData)

            dragAdapter.notifyDataSetChanged()

            Navigation.findNavController(it).navigateUp()
        }

        //完成监听
        dataBinDing.btSuccess.setOnClickListener {
            editData(dragAdapter)
            //更新数据
            viewModel.upData(requireContext(), dragAdapter.data as ArrayList<TempDragBean>)

            //回到上一个页面
            Navigation.findNavController(it).navigateUp()
        }

        dragAdapter.itemClick = object : DragAdapter.OnParentClick {

            //编辑 加号 减号 响应
            override fun itemIsAdd(
                isAdd: Boolean,
                parentPosition: Int,
                childPosition: Int,
                dragChildAdapter: DragChildAdapter,
                childData: TempDragBean.childData,
            ) {

                Log.i("szj点击的是:",
                    "${if (isAdd) "+" else "-"} parent:${parentPosition}\tchild:${childPosition}")

                // 加
                if (isAdd) {
                    if (dragAdapter.data[0].child.size >= 4) {
                        "最多只能添加4个" toast requireContext()
                        return
                    }

                    // 先删除需要添加的
                    dragChildAdapter.remove(childPosition)

                    //添加到头部
                    dragAdapter.childAdapterList[0].addData(childData)

                } else {
                    //减
                    if (dragAdapter.data[0].child.size == 1) {
                        "至少保留一个" toast requireContext()
                        return
                    }
                    //当前适配器删除下标
                    dragChildAdapter.remove(childPosition)
                    Log.i("szj点击的信息", childData.toString())

                    //添加到对应的组中
                    dragAdapter.childAdapterList[childData.parentId].addData(childData)
                }

                dragAdapter.notifyDataSetChanged()
                dragChildAdapter.notifyDataSetChanged()
            }

            //点击子View响应
            override fun childViewClick(childData: TempDragBean.childData) {
                viewModel.actionJump(requireActivity(), childData, view)
            }
        }

        //编辑按钮点击
        dataBinDing.onClickListener = View.OnClickListener {
            dragAdapter.data.forEach {
                it.isAddVisibility = !it.isAddVisibility
            }
            dragAdapter.notifyDataSetChanged()
        }
    }

    //当点击退出/完成时候 编辑按钮为false【不显示+号-号】
    private fun editData(dragAdapter: DragAdapter) {
        //编辑不选择
        dragAdapter.data.forEach {
            it.isAddVisibility = false
        }
    }
}