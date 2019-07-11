package com.lxj.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import com.lxj.statelayout.StateLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var stateLayout: StateLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stateLayout = StateLayout(this)
                .config(retryAction = {
                    Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
                })
                .wrap(this)
                .showLoading()

        Handler().postDelayed({
            stateLayout.showContent()
        }, 1000)


        // create StateLayout for textView
        val layout2 = StateLayout(this)
                .config(retryAction = {
                            Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
                        })
                .wrap(view_content)
        btn_loading.setOnClickListener {
            layout2.showLoading()
        }
        btn_empty.setOnClickListener { layout2.showEmpty() }
        btn_error.setOnClickListener { layout2.showError() }
        btn_content.setOnClickListener { layout2.showContent() }
        Handler().postDelayed({
            layout2.showContent()
        }, 1000)


        //登录按钮，由于按钮太小，不时候使用默认的各个状态的布局，需要自定义
        val loginStateLayout = StateLayout(this)
                .config(loadingLayoutId = R.layout.custom_loading,
                        errorLayoutId = R.layout.custom_error,
                        emptyLayoutId = R.layout.custom_empty,
                        useContentBgWhenLoading = true,
                        retryAction = {
                            Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
                        })
                .wrap(login)
        login.setOnClickListener {
            loginStateLayout.showLoading()
            Handler().postDelayed({
                loginStateLayout.showContent()
                //                loginStateLayout.showEmpty()
                //                loginStateLayout.showError()
            }, 1500)
        }

        //关注按钮
        val focusStateLayout = StateLayout(this)
                .config(loadingLayoutId = R.layout.custom_loading,
                        errorLayoutId = R.layout.custom_error,
                        emptyLayoutId = R.layout.custom_empty,
                        useContentBgWhenLoading = true,
                        retryAction = {
                            Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
                        })
                .wrap(focus)
        focus.setOnClickListener {
            focusStateLayout.showLoading()
            Handler().postDelayed({
                focusStateLayout.showContent()
            }, 1500)
        }


        btn_in_fragment.setOnClickListener {
            startActivity(Intent(this, InFragmentDemo::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_loading -> stateLayout.showLoading()
            R.id.item_empty -> stateLayout.showEmpty("没有数据了啊")
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
