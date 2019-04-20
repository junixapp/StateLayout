package com.lxj.demo

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_in_fragment.*

class InFragmentDemo: AppCompatActivity(){
    val fragments = arrayListOf<BaseFragment>(
            DemoFragment(), DemoFragment(), DemoFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_fragment)
        pager.adapter = DemoAdapter()
    }

    inner class DemoAdapter : FragmentPagerAdapter(supportFragmentManager){
        override fun getItem(p: Int) = fragments[p]

        override fun getCount() = fragments.size
    }

}