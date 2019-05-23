package com.lxj.demo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import com.lxj.statelayout.StateLayout
import kotlinx.android.synthetic.main.activity_main2.*

class WithMarginDemo : AppCompatActivity() {
    lateinit var stateLayout: StateLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        stateLayout = StateLayout(this)
                .config(loadingLayoutId = R.layout.custom_loading2)
                .wrap(text)
        stateLayout.setBackgroundColor(Color.parseColor("#f01111"))

    }

}
