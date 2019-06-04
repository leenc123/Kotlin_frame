package com.leen.kotlin_demo.application

import com.leen.kotlin_library.application.MyApplication
import com.sjtu.yifei.route.Routerfit

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *
 */
class WeApplication :MyApplication() {
    val isDebug=true
    override fun onCreate() {
        super.onCreate()
        //在你的application onCreate()方法中
        Routerfit.init(this)
    }
}