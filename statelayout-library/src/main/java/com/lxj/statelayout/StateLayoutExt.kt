package com.lxj.statelayout

import android.view.LayoutInflater

// some extentions
fun StateLayout.setLoadingRes(resId: Int): StateLayout{
    loadingView = LayoutInflater.from(context).inflate(resId,this, false)
    return this
}

fun StateLayout.setEmptyRes(resId: Int): StateLayout{
    emptyView = LayoutInflater.from(context).inflate(resId,this, false)
    return this
}

fun StateLayout.setErrorRes(resId: Int): StateLayout{
    errorView = LayoutInflater.from(context).inflate(resId,this, false)
    return this
}

fun StateLayout.setDefaultState(s: State): StateLayout{
    this.state = s
    return this
}

fun StateLayout.customViewRes(loadingResId: Int = 0,
                              emptyResId: Int = 0,
                              errorResId: Int = 0): StateLayout{
    when{
        loadingResId!=0 -> return setLoadingRes(loadingResId)
        emptyResId!=0 -> return setEmptyRes(loadingResId)
        errorResId!=0 -> return setErrorRes(loadingResId)
    }
    return this
}

fun StateLayout.config(hasLoadingOverlay: Boolean = false,
                       animDuration: Long = 0L,
                       loadingOverlayColor: Int = 0): StateLayout{
    this.hasLoadingOverlay = hasLoadingOverlay
    if(animDuration!=0L){
        this.animDuration = animDuration
    }
    if(loadingOverlayColor!=0){
        this.loadingOverlayColor = loadingOverlayColor
    }
    return this
}