package com.lxj.statelayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.lxj.statelayout.State.*


class StateLayout : FrameLayout {
    var state = Loading // default state
    var loadingView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_loading,this, false)
    var emptyView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_empty,this, false)
    var errorView: View = LayoutInflater.from(context).inflate(R.layout._loading_layout_error,this, false)
    var contentView: View? = null
    var hasLoadingOverlay: Boolean = false
    var loadingOverlayColor = Color.parseColor("#DCFFFFFF")
    var animDuration = 250L

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun wrap(view: View?): StateLayout {
        if (view == null) {
            throw IllegalArgumentException("view can not be null")
        }
        view.post {
            // 1.apply self width&height
            val temp = ViewGroup.LayoutParams(0,0)
            temp.width = view.measuredWidth
            temp.height = view.measuredHeight
            layoutParams = temp

            // 2.remove view
            val parent = view.parent as ViewGroup
            val lp = view.layoutParams
            val index = parent.indexOfChild(view)
            parent.removeView(view)

            // 3.wrap view as container
            view.visibility = View.INVISIBLE
            view.alpha = 0f
            addView(view, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            prepareStateView()
            contentView = view

            // 4.add to parent
            parent.addView(this, index, cloneParams(lp, temp.width, temp.height))

            // 5.show default state
            switchLayout(state)
        }
        return this
    }

    fun wrap(activity: Activity): StateLayout = wrap((activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0))

    fun wrap(fragment: Fragment): StateLayout = wrap(fragment.view)

    private fun cloneParams(src: ViewGroup.LayoutParams, newWidth: Int, newHeight: Int): ViewGroup.LayoutParams{
        val lp = when(src){
            is ConstraintLayout.LayoutParams -> ConstraintLayout.LayoutParams(src)
            is MarginLayoutParams -> MarginLayoutParams(src) // donot lose margin
            else -> LayoutParams(src)
        }
        lp.width = newWidth
        lp.height = newHeight
        return lp
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (contentView == null) {
            throw IllegalArgumentException("contentView can not be null!")
        }
    }

    private fun prepareStateView() {
        with(emptyView) {
            visibility = View.INVISIBLE
            alpha = 0f
            addView(this)
        }
        with(errorView) {
            visibility = View.INVISIBLE
            alpha = 0f
            addView(this)
        }
        with(loadingView) {
            visibility = View.INVISIBLE
            alpha = 0f
            addView(this)
        }
        bringChildToFront(loadingView)
        if (hasLoadingOverlay) {
            loadingView.setBackgroundColor(loadingOverlayColor)
        }
    }

    private fun switchLayout(s: State = Loading) {
        state = s
        // ensure show is called behind wrap!
        post {
            when (state) {
                Loading -> show(loadingView)
                Empty -> show(emptyView)
                Error -> show(errorView)
                Content -> show(contentView)
            }
        }
    }

    fun showLoading() {
        switchLayout(Loading)
    }

    fun showContent() {
        switchLayout(Content)
    }

    fun showEmpty() {
        switchLayout(Empty)
    }

    fun showError() {
        switchLayout(Error)
    }

    private fun show(v: View?) {
        if (v == null) {
            throw IllegalArgumentException("contentView can not be null!")
        }
        for (i in 0..childCount) {
            val child = getChildAt(i)
            if (child == v) {
                showAnim(child)
            } else {
                //hide others
                if (hasLoadingOverlay && child == contentView && state == Loading) {
                    continue
                }
                hideAnim(child)
            }
        }
    }

    private fun showAnim(v: View?) {
        if (v == null || v.visibility == View.VISIBLE) return
        v.animate().alpha(1f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        v.visibility = View.VISIBLE
                    }
                })
                .start()
    }

    private fun hideAnim(v: View?) {
        if (v == null || v.visibility == View.INVISIBLE) return
        v.animate().alpha(0f).setDuration(animDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        v.visibility = View.INVISIBLE
                    }
                })
                .start()
    }

}