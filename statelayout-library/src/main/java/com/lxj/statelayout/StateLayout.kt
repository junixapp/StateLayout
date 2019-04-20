package com.lxj.statelayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import com.lxj.statelayout.State.*


class StateLayout : FrameLayout {
    var state = Content // default state
    var loadingView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_loading, this, false)
    var emptyView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_empty, this, false)
    var errorView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_error, this, false)
    var contentView: View? = null
    var animDuration = 200L
    var useContentBgWhenLoading = false //是否在Loading状态使用内容View的背景

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun wrap(view: View?): StateLayout {
        if (view == null) {
            throw IllegalArgumentException("view can not be null")
        }
        with(emptyView) {
            visibility = View.INVISIBLE
            alpha = 0f
        }
        with(errorView) {
            visibility = View.INVISIBLE
            alpha = 0f
            findViewById<View>(R.id.btn_retry)?.setOnClickListener { mRetryAction?.invoke(errorView) }
            setOnClickListener {
                showLoading()
                mRetryAction?.invoke(errorView)
            }
        }
        with(loadingView) {
            visibility = View.INVISIBLE
            alpha = 0f
        }
        prepareStateView()

        view.visibility = View.INVISIBLE
        view.alpha = 0f
        if (view.parent == null) {
            //no attach parent.
            addView(view, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            contentView = view
        } else {
//            view.post {
                // 1.remove self
                val parent = view.parent as ViewGroup
                val lp = view.layoutParams
//                lp.width = view.measuredWidth
//                lp.height = view.measuredHeight
                val index = parent.indexOfChild(view)
                parent.removeView(view)
                // 2.wrap view as a parent
                addView(view, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

                // 3.add this to original parent
                parent.addView(this, index, lp)
                contentView = view
//            }
        }
        switchLayout(state)
        return this
    }

    fun wrap(activity: Activity): StateLayout = wrap((activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0))

    fun wrap(fragment: Fragment): StateLayout = wrap(fragment.view)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (contentView == null) {
            throw IllegalArgumentException("contentView can not be null!")
        }
    }

    private fun prepareStateView() {
        addView(emptyView)
        addView(errorView)
        addView(loadingView)
    }

    private fun switchLayout(s: State) {
        state = s
        when (state) {
            Loading -> {
                switch(loadingView)
                if (useContentBgWhenLoading && contentView?.background != null) {
                    background = contentView?.background
                }
            }
            Empty -> {
                switch(emptyView)
            }
            Error -> {
                switch(errorView)
            }
            Content -> {
                switch(contentView)
            }
        }
    }

    fun showLoading(): StateLayout {
        switchLayout(Loading)
        return this
    }

    fun showContent(): StateLayout {
        switchLayout(Content)
        return this
    }

    fun showEmpty(): StateLayout {
        switchLayout(Empty)
        return this
    }

    fun showError(): StateLayout {
        switchLayout(Error)
        return this
    }

    private fun switch(v: View?) {
        v?.post {
            for (i in 0..childCount) {
                val child = getChildAt(i)
                if (child == v) {
                    showAnim(child)
                } else {
                    //hide others
                    hideAnim(child)
                }
            }
        }
    }

    private fun showAnim(v: View?) {
        if (v == null || v.visibility == View.VISIBLE) return
        v.animate().cancel()
        v.animate().alpha(1f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        v.visibility = View.VISIBLE
                    }
                })
                .start()
    }

    private fun hideAnim(v: View?) {
        if (v == null ) return
        v.animate().cancel()
        v.visibility = View.INVISIBLE
        v.alpha = 0f
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (state == Loading && loadingView.visibility == View.VISIBLE) return true
        return super.dispatchTouchEvent(ev)
    }


    private var mRetryAction: ((errView: View) -> Unit)? = null

    /**********  自定义配置  **********/
    /**
     * 设置加载中的布局
     */
    private fun setLoadingLayout(layoutId: Int): StateLayout {
        loadingView = LayoutInflater.from(context).inflate(layoutId, this, false)
        (loadingView.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
        return this
    }

    /**
     * 设置数据为空的布局
     */
    private fun setEmptyLayout(layoutId: Int): StateLayout {
        emptyView = LayoutInflater.from(context).inflate(layoutId, this, false)
        (emptyView.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
        return this
    }

    /**
     * 设置加载失败的布局
     */
    private fun setErrorLayout(layoutId: Int): StateLayout {
        errorView = LayoutInflater.from(context).inflate(layoutId, this, false)
        (errorView.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
        return this
    }

    /**
     * 设置三种状态的布局
     */
    fun customStateLayout(loadingLayoutId: Int = 0,
                          emptyLayoutId: Int = 0,
                          errorLayoutId: Int = 0): StateLayout {
        if (loadingLayoutId != 0) setLoadingLayout(loadingLayoutId)
        if (emptyLayoutId != 0) setEmptyLayout(emptyLayoutId)
        if (errorLayoutId != 0) setErrorLayout(errorLayoutId)

        return this
    }

    /**
     * 设置一些配置
     * @param useContentBgWhenLoading 是否在加载状态下使用contentView的背景
     * @param animDuration 遮照显示和隐藏的动画时长
     */
    fun config(useContentBgWhenLoading: Boolean = false,
               animDuration: Long = 0L,
               retryAction: ((errView: View) -> Unit)? = null): StateLayout {
        if (useContentBgWhenLoading) {
            this.useContentBgWhenLoading = useContentBgWhenLoading
        }
        if (animDuration != 0L) {
            this.animDuration = animDuration
        }
        mRetryAction = retryAction
        return this
    }
}