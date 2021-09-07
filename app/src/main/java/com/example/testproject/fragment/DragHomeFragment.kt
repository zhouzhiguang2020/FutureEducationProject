package com.example.testproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.adapter.DragAdapter
import com.example.testproject.adapter.DragChildAdapter
import com.example.testproject.base.TempDragBean
import com.example.testproject.databinding.DragHomeBinDing
import com.example.testproject.template.DragTemplate
import com.example.testproject.view_model.DragViewModel

/**
 *
 * @Package: com.example.testproject.fragment
 * @ClassName: DragHomeFragment
 * @Author: szj
 * @CreateDate: 9/6/21
 */
class DragHomeFragment : Fragment() {

    private lateinit var dragHomeBinDing: DragHomeBinDing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dragHomeBinDing = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_drag_home,
            container,
            false
        )
        return dragHomeBinDing.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: DragViewModel =
            ViewModelProvider(requireActivity()).get(DragViewModel::class.java)

        viewModel.isHome = true

        val dataList = ArrayList<TempDragBean>()

        //首页只展示下标为0的条目
        dataList.add(viewModel.getData(requireContext())[0])

        val template = DragTemplate(dataList, true)
        template.template(requireContext(), dragHomeBinDing.recyclerView)
        val dragAdapter: DragAdapter = template.getAdapter() as DragAdapter

        viewModel.liveData.observe(requireActivity()) {
            // 监听两个页面之间的变化
            var isMore = false
            it[0].child.forEach {
                if (it.title == getString(R.string.more)) {
                    isMore = true
                }
            }

            //没有更多 并且当前是首页 添加更多
            if (!isMore && viewModel.isHome) {
                it[0].child += TempDragBean.childData(getString(R.string.more), R.drawable.drawable_green, -1)
            }

            //首页只显示 条目一的数据
            dragAdapter.setNewData(listOf(it[0]))
        }

        //  点击监听
        dragAdapter.itemClick = object : DragAdapter.OnParentClick {
            override fun itemIsAdd(
                isAdd: Boolean,
                parentPosition: Int,
                childPosition: Int,
                dragChildAdapter: DragChildAdapter,
                childData: TempDragBean.childData,
            ) {
            }

            override fun childViewClick(childData: TempDragBean.childData) {
                // 跳转页面
                viewModel.actionJump(requireActivity(), childData, view)
            }
        }
    }
}