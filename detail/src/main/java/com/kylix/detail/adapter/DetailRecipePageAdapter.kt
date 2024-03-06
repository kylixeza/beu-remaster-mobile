package com.kylix.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailRecipePageAdapter (
    fm: FragmentManager,
    lifeCycle: Lifecycle,
): FragmentStateAdapter(fm, lifeCycle) {

    private val fragments: MutableList<Fragment> = mutableListOf()

    fun submitFragments(fragments: List<Fragment>) {
        this.fragments.clear()
        this.fragments.addAll(fragments)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}