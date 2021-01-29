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

在布局中使用:
```
<com.lxj.statelayout.StateLayout
            android:id="@+id/slInLayout"
            android:layout_marginBottom="40dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <TextView
                android:id="@+id/tvInLayout"
                android:gravity="center"
                android:text="测试布局中使用"
                android:background="#9f00"
                android:textColor="#fff"
                android:textSize="20sp"
                android:layout_width="match_parent"
                android:layout_height="150dp"/>
        </com.lxj.statelayout.StateLayout>
```

自定义每种状态对应的布局:
```kotlin
StateLayout(this)
    .config(loadingLayoutId = R.layout.custom_loading, //自定义加载中布局
            errorLayoutId = R.layout.custom_error, //自定义加载失败布局
            emptyLayoutId = R.layout.custom_empty, //自定义数据位为空的布局
            useContentBgWhenLoading = true, //加载过程中是否使用内容的背景，默认false
            enableLoadingShadow = true, //加载过程中是否启用半透明阴影盖在内容上面，默认false
            defaultShowLoading = true, //是否初始显示loading状态，默认显示的是Content
            enableTouchWhenLoading = true, //显示loading状态是否允许触摸，默认false
            noEmptyAndError = true, //是否去除Empty和Error状态，有时候只需要一个Loading状态，可以减少内存，默认false
            showLoadingOnce = false, //是否只显示一次Loading，在某些时候需要，默认false
            retryAction = { //点击errorView的回调
                Toast.makeText(this, "点击了重试", Toast.LENGTH_SHORT).show()
            })
    .wrap(view)
    .showLoading()
```

也可以全局配置，全局配置适用于所有的实例，但会被每个实例自己的配置覆盖：
```kotlin
StateLayoutConfig.init(...)
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