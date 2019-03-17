package com.lxj.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class InFragmentDemo: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_fragment)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, DemoFragment())
                .commit()
    }
}