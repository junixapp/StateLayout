# StateLayout
Simple way to change your layout state, like loading, empty, error. Strong customizitaion, written by Kotlin.


# Feature
- 对已有布局零侵入，无需修改现有布局
- 支持对Activity, Fragment, View进行状态切换
- 支持自定义每种状态的布局
- 暴露失败状态View点击回调
- 暂时不支持对ConstraintLayout中的View进行状态切换

# ScreenShot
![StateLayout](/screenshot/preview.gif)
![StateLayout](/screenshot/fragment.gif)


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

默认是显示内容布局，改变状态的方法:
```kotlin
stateLayout.showLoading()
stateLayout.showContent() //default state
stateLayout.showError()
stateLayout.showEmpty()
```

自定义每种状态对应的布局:
```kotlin
StateLayout(this)
    .config(loadingLayoutId = R.layout.custom_loading, //自定义加载中布局
            errorLayoutId = R.layout.custom_error, //自定义加载失败布局
            emptyLayoutId = R.layout.custom_empty, //自定义数据位为空的布局
            useContentBgWhenLoading = true, //加载过程中是否使用内容的背景
            enableLoadingShadow = true, //加载过程中是否启用半透明阴影盖在内容上面
            retryAction = { //点击errorView的回调
                Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
            })
    .wrap(view)
    .showLoading()
```

在Fragment中使用的时候需要注意下，要将StateLayout作为Fragment的View返回：
```kotlin
private var stateLayout: StateLayout? = null
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    if (fragmentView==null) {
        fragmentView = inflater.inflate(getLayoutId(), container, false)
        stateLayout = StateLayout(context!!)
                .wrap(fragmentView)
                .showLoading()
    }
    return stateLayout
}
```