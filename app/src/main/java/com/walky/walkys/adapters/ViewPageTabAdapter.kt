package com.walky.walkys.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPageTabAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!) {
    private val mFragmentList: MutableList<Fragment>
    private val mFragmentTitleList: MutableList<String>
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun getCount(): Int {
        Log.d("ViewPagerSize", "" + mFragmentList.size)
        return mFragmentList.size
    }

    init {
        mFragmentList = ArrayList()
        mFragmentTitleList = ArrayList()
    }
}