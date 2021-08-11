package com.example.testproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *
 * @Package: com.example.testproject.adapter
 * @ClassName: ViewPagerAdapter
 * @Author: szj
 * @CreateDate: 8/9/21 5:51 PM
 */
class ViewPagerAdapter(fm: FragmentManager, private val list: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position]
}