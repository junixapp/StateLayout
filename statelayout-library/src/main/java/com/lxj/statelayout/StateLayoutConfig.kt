package com.lxj.statelayout

/**
 * 全局配置，对所有实例生效，但会被实例自己的配置覆盖
 */
object StateLayoutConfig {
    var animDuration = 120L
    var useContentBgWhenLoading = false //是否在Loading状态使用内容View的背景
    var enableLoadingShadow = false //是否启用加载状态时的半透明阴影
    var emptyText: String = ""
    var emptyIcon: Int = 0
    var enableTouchWhenLoading = false
    var defaultShowLoading = false
    var noEmptyAndError = false //是否去除empty和error状态，有时候只需要一个loading状态，这样减少内存
    var showLoadingOnce = false //是否只显示一次Loading
    var loadingLayoutId = R.layout._loading_layout_loading
    var emptyLayoutId = R.layout._loading_layout_empty
    var errorLayoutId = R.layout._loading_layout_error

    fun init(animDuration: Long? = null, useContentBgWhenLoading: Boolean? = null,
             enableLoadingShadow: Boolean? = null, enableTouchWhenLoading: Boolean? = null,
             defaultShowLoading: Boolean? = null, noEmptyAndError: Boolean? = null,
             showLoadingOnce: Boolean? = null, emptyText: String? = null,
             emptyIcon: Int? = null, loadingLayoutId: Int? = null,
             emptyLayoutId: Int? = null, errorLayoutId: Int? = null
    ){
        if(animDuration!=null) this.animDuration = animDuration
        if(useContentBgWhenLoading!=null) this.useContentBgWhenLoading = useContentBgWhenLoading
        if(enableLoadingShadow!=null) this.enableLoadingShadow = enableLoadingShadow
        if(enableTouchWhenLoading!=null) this.enableTouchWhenLoading = enableTouchWhenLoading
        if(defaultShowLoading!=null) this.defaultShowLoading = defaultShowLoading
        if(noEmptyAndError!=null) this.noEmptyAndError = noEmptyAndError
        if(showLoadingOnce!=null) this.showLoadingOnce = showLoadingOnce
        if(emptyText!=null) this.emptyText = emptyText
        if(emptyIcon!=null) this.emptyIcon = emptyIcon
        if(loadingLayoutId!=null) this.loadingLayoutId = loadingLayoutId
        if(emptyLayoutId!=null) this.emptyLayoutId = emptyLayoutId
        if(errorLayoutId!=null) this.errorLayoutId = errorLayoutId
    }
}