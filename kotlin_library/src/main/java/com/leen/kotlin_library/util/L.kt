package com.leen.kotlin_library.util

import android.util.Log

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：log日志打印
 *
 */
object L {
    var isDebug = true // 是否需要打印bug，可以在application的onCreate函数里面初始化
    val TAG = "=======app_ebiz======="

    // 下面四个是默认tag的函数
    fun i(msg: String) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }

    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }

    // 下面是传入类名打印log
    fun i(_class: Class<*>, msg: String) {
        if (isDebug) {
            Log.i(_class.name, msg)
        }
    }

    fun d(_class: Class<*>, msg: String) {
        if (isDebug) {
            Log.d(_class.name, msg)
        }
    }

    fun e(_class: Class<*>, msg: String) {
        if (isDebug) {
            Log.e(_class.name, msg)
        }
    }

    fun v(_class: Class<*>, msg: String) {
        if (isDebug) {
            Log.v(_class.name, msg)
        }
    }

    // 下面是传入自定义tag的函数
    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }
}
