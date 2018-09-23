package com.lxj.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import com.lxj.statelayout.StateLayout
import com.lxj.statelayout.config
import com.lxj.statelayout.setLoadingRes
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    lateinit var stateLayout: StateLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        stateLayout = StateLayout(this)
                .setLoadingRes(R.layout.custom_loading2)
                .wrap(text)

    }

}
