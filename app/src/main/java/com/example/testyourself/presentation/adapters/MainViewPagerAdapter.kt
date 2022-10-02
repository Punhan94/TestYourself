package com.example.testyourself.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(fm:FragmentManager, lifecycle: Lifecycle,var list: List<Fragment>)
    :FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount()=list.size

    override fun createFragment(position: Int)=list[position]
}