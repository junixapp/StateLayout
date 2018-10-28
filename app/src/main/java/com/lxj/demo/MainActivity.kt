package com.lxj.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import com.lxj.statelayout.State
import com.lxj.statelayout.StateLayout
import com.lxj.statelayout.config
import com.lxj.statelayout.setDefaultState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var stateLayout: StateLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stateLayout = StateLayout(this)
//                .setLoadingRes(R.layout.custom_loading)
                .setDefaultState(State.Content)
                .config(hasLoadingOverlay = true, animDuration = 400)
                .wrap(this)
        Handler().postDelayed({
            stateLayout.showContent()
        }, 1500)

        // create StateLayout for textView
        val layout2 = StateLayout(this).wrap(view_content)

        btn_loading.setOnClickListener{
            layout2.showLoading()
        }
        btn_empty.setOnClickListener { layout2.showEmpty() }
        btn_error.setOnClickListener { layout2.showError() }
        btn_content.setOnClickListener { layout2.showContent() }


        // StateLayout for Another TextView
        val layout3 = StateLayout(this).wrap(tv_content)
        btn_loading2.setOnClickListener{layout3.showLoading() }
        btn_empty2.setOnClickListener { layout3.showEmpty() }
        btn_error2.setOnClickListener { layout3.showError() }
        btn_content2.setOnClickListener { layout3.showContent() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_loading -> stateLayout.showLoading()
            R.id.item_empty -> stateLayout.showEmpty()
            R.id.item_error -> stateLayout.showError()
            R.id.item_content -> stateLayout.showContent()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
