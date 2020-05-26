package com.lxj.demo1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
