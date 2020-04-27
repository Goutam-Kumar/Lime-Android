package com.lime.android.fragments.orderhistory

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lime.android.R
import com.lime.android.fragments.orderhistory.completedorder.CompletedOrderFragment
import com.lime.android.fragments.orderhistory.currentorder.CurrentOrderFragment
import com.lime.android.ui.BaseFragment

class OrderHistoryAdapter(context: Context, fm: FragmentManager, behavior: Int):
    FragmentStatePagerAdapter(fm, behavior){

    private val mFragmentList = ArrayList<BaseFragment>()
    private val mFragmentTitleList = ArrayList<String>()
    init {
        mFragmentList.apply {
            add(CurrentOrderFragment())
            add(CompletedOrderFragment())
        }
        mFragmentTitleList.apply {
            add(context.getString(R.string.in_progress))
            add(context.getString(R.string.completed))
        }
    }
    override fun getItem(position: Int): Fragment {
       return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
}
