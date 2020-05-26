package com.lxj.demo1

import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
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