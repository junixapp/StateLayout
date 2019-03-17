package com.lxj.demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lxj.statelayout.StateLayout
import kotlinx.android.synthetic.main.fragment_demo.*

class DemoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stateLayout = StateLayout(context!!).wrap(this)

        stateLayout.postDelayed({
            stateLayout.showContent()
        }, 1500)
        btn_loading.setOnClickListener {
            stateLayout.showLoading()
            it.postDelayed({stateLayout.showContent()}, 1000)
        }
    }
}
