package com.lxj.demo

import android.view.View
import android.widget.TextView

class DemoFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_demo
    }

    override fun init(view: View) {
        //load data
        val textView = view.findViewById<TextView>(R.id.text)
        textView.text = "${textView.text} - 当前时间${System.currentTimeMillis()}"
    }

}
