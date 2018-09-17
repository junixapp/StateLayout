# StateLayout
Simple way to change your layout state, like loading, empty, error. Strong customizitaion, written by Kotlin.

# Feature
- simple API to use
- support Activity, Fragment, View
- support custom state layout

# ScreenShot
![StateLayout](/screenshot/preview.gif)

# Usage
use in activity/fragment:
```
val stateLayout = StateLayout(this).wrap(this)
```
use for custom layout:
```
val layout2 = StateLayout(this).wrap(fl_custom)
```
custom default state layout:
```
StateLayout(this)
    .setLoadingRes(R.layout.custom_loading)
    .wrap(this)
```
change state api:
```
stateLayout.showLoading() //default state
stateLayout.showContent()
stateLayout.showError()
stateLayout.showEmpty()
```

# Gradle
[ ![Download](https://api.bintray.com/packages/li-xiaojun/jrepo/statelayout/images/download.svg) ](https://bintray.com/li-xiaojun/jrepo/statelayout/_latestVersion)
```
compile 'com.lxj:statelayout:latest release'
```