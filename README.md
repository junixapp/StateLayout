# StateLayout
Simple way to change your layout state, like loading, empty, error. Strong customizitaion, written by Kotlin.


# Feature
- 对已有布局零侵入，无需修改现有布局
- 支持对Activity, Fragment, View进行状态切换
- 支持自定义每种状态的布局
- 暴露失败状态View点击回调

# ScreenShot
![StateLayout](/screenshot/preview.gif)


# Gradle
[ ![Download](https://api.bintray.com/packages/li-xiaojun/jrepo/statelayout/images/download.svg) ](https://bintray.com/li-xiaojun/jrepo/statelayout/_latestVersion)
```
implementation 'com.lxj:statelayout:最新版本号'
```

# Usage
对Activity/Fragment使用:
```kotlin
val stateLayout = StateLayout(this)
            .wrap(this)
            .showLoading()
```

对指定View使用:
```kotlin
val layout2 = StateLayout(this)
            .wrap(view)
            .showLoading()
```

改变状态:
```kotlin
stateLayout.showLoading() //default state
stateLayout.showContent()
stateLayout.showError()
stateLayout.showEmpty()
```

自定义每种状态对应的布局:
```kotlin
StateLayout(this)
    .customStateLayout(loadingLayoutId = R.layout.custom_loading, //自定义加载中布局
            errorLayoutId = R.layout.custom_error, //自定义加载失败布局
            emptyLayoutId = R.layout.custom_empty) //自定义数据位为空的布局
    .config(useContentBgWhenLoading = true, //加载过程中是否使用内容的背景
            retryAction = { //点击errorView的回调
                Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
            })
    .wrap(view)
    .showLoading()
```

