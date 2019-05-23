package com.leen.kotlin_library.application

import android.app.Application
import me.yokeyword.fragmentation.BuildConfig
import me.yokeyword.fragmentation.Fragmentation

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：
 *
 */
open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.SHAKE)
                .debug(BuildConfig.DEBUG)
                .install()

    }
}