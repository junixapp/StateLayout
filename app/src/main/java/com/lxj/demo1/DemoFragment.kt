package com.lxj.demo1

import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.ViewHolder
import kotlinx.android.synthetic.main.fragment_demo.*

class DemoFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_demo
    }

    override fun init(view: View) {
        //load data
        stateLayout.showLoading()

        Handler().postDelayed({
            stateLayout.showContent()

            rv.layoutManager = LinearLayoutManager(context)
            rv.adapter = object : EasyAdapter<Int>(listOf(1, 1, 1, 1, 1), android.R.layout.simple_list_item_1) {
                override fun bind(holder: ViewHolder, t: Int, position: Int) {
                    holder.getView<TextView>(android.R.id.text1).text = "index = ${position}"
                }
            }
        }, 600)
    }

}
