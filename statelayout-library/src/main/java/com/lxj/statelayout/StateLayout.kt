package com.lxj.statelayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.lxj.statelayout.State.*
import org.w3c.dom.Text


class StateLayout : FrameLayout {
    var state = None // default state
    var loadingView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_loading, this, false)
    var emptyView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_empty, this, false)
    var errorView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_error, this, false)
    var contentView: View? = null
    var animDuration = 250L
    var useContentBgWhenLoading = false //是否在Loading状态使用内容View的背景
    var enableLoadingShadow = false //是否启用加载状态时的半透明阴影

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
            findViewById<View>(R.id.btn_retry)?.setOnClickListener {
                showLoading()
                mRetryAction?.invoke(errorView)
            }
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
            // 1.remove self from parent
            val parent = view.parent as ViewGroup
            val lp = view.layoutParams
            val index = parent.indexOfChild(view)
            parent.removeView(view)
            // 2.wrap view as a parent
            addView(view, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

            // 3.add this to original parent，暂不支持parent为ConstraintLayout
            parent.addView(this, index, lp)
            contentView = view
        }
        switchLayout(Content)
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
        if (state == s) return
        state = s
        when (state) {
            Loading -> {
                switch(loadingView)
                if (useContentBgWhenLoading && contentView?.background != null) {
                    background = contentView?.background
                }
                if (enableLoadingShadow) {
                    loadingView.setBackgroundColor(Color.parseColor("#66000000"))
                } else {
                    loadingView.setBackgroundResource(0)
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

    fun showLoading(showText: Boolean = true): StateLayout {
        switchLayout(Loading)
        val textView = loadingView.findViewById<TextView>(R.id.tvLoading)
        textView?.visibility = if(showText) View.VISIBLE else View.GONE
        return this
    }

    fun showContent(): StateLayout {
        switchLayout(Content)
        return this
    }

    fun showEmpty(noDataText: String = "暂无数据", noDataIconRes: Int = R.drawable._statelayout_empty): StateLayout {
        switchLayout(Empty)
        val textView = emptyView.findViewById<TextView>(R.id.tvNoDataText)
        textView?.text = noDataText
        val imageView = emptyView.findViewById<ImageView>(R.id.ivNoDataIcon)
        imageView?.setImageResource(noDataIconRes)
        return this
    }

    fun showError(): StateLayout {
        switchLayout(Error)
        return this
    }

    private fun switch(v: View?) {
        if (switchTask != null) {
            removeCallbacks(switchTask)
        }
        switchTask = SwitchTask(v!!)
        post(switchTask)
    }

    var switchTask: SwitchTask? = null

    inner class SwitchTask(private var target: View) : Runnable {
        override fun run() {
            for (i in 0..childCount) {
                if (state == Loading && enableLoadingShadow && getChildAt(i) == contentView) continue
                hideAnim(getChildAt(i))
            }
            showAnim(target)
        }
    }

    private fun showAnim(v: View?) {
        if (v == null) return
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
        if (v == null) return
        v.animate().cancel()
        v.animate().alpha(0f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        v.visibility = View.INVISIBLE
                    }
                })
                .start()
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
     * 自定义一些配置
     * @param loadingLayoutId 加载时的布局
     * @param emptyLayoutId 数据为空时的布局
     * @param errorLayoutId 加载失败的布局
     * @param useContentBgWhenLoading 是否在加载状态下使用contentView的背景
     * @param animDuration 遮照显示和隐藏的动画时长
     * @param enableLoadingShadow 是否启用加载时的半透明阴影
     * @param retryAction 加载失败状态下点击重试的行为
     */
    fun config(loadingLayoutId: Int = 0,
               emptyLayoutId: Int = 0,
               errorLayoutId: Int = 0,
               useContentBgWhenLoading: Boolean = false,
               animDuration: Long = 0L,
               enableLoadingShadow: Boolean = false,
               retryAction: ((errView: View) -> Unit)? = null): StateLayout {
        if (loadingLayoutId != 0) setLoadingLayout(loadingLayoutId)
        if (emptyLayoutId != 0) setEmptyLayout(emptyLayoutId)
        if (errorLayoutId != 0) setErrorLayout(errorLayoutId)
        if (useContentBgWhenLoading) {
            this.useContentBgWhenLoading = useContentBgWhenLoading
        }
        if (animDuration != 0L) {
            this.animDuration = animDuration
        }
        this.enableLoadingShadow = enableLoadingShadow
        mRetryAction = retryAction
        return this
    }
}