package com.lxj.demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lxj.statelayout.StateLayout


/**
 * Description:
 * Create by dance, at 2019/4/19
 */
abstract class BaseFragment : Fragment() {
    private var fragmentView: View? = null
    private var isInit = false
    private var stateLayout: StateLayout? = null

    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragmentView==null) {
            fragmentView = inflater.inflate(getLayoutId(), container, false)
            stateLayout = StateLayout(context!!)
                    .wrap(fragmentView)
                    .showLoading()
        }
        return stateLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        safeInit()
    }

    private fun safeInit() {
        if (userVisibleHint && fragmentView!=null) {
            if (!isInit) {
                isInit = true
                init(fragmentView!!)
                stateLayout?.apply {
                    postDelayed({ showContent() },500)
                }

            }
        }
    }

    abstract fun init(view: View)

    //当tab被选中时执行，但是先于onCreateView执行
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        safeInit()
    }
}