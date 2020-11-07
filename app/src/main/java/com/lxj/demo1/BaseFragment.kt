package com.lxj.demo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Description:
 * Create by dance, at 2019/4/19
 */
abstract class BaseFragment : Fragment() {
    private var fragmentView: View? = null
    private var isInit = false

    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragmentView==null) {
            fragmentView = inflater.inflate(getLayoutId(), container, false)
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        safeInit()
    }

    override fun onResume() {
        super.onResume()
        safeInit()
    }

    private fun safeInit() {
        if (userVisibleHint && fragmentView!=null) {
            if (!isInit) {
                isInit = true
                init(fragmentView!!)
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