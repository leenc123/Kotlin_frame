package com.leen.kotlin_library.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：应用程序Activity管理类
 *
 */
class AppManager private constructor() {
    private var mActivityStack: Stack<Activity>? = null

    companion object {
        private var mAppManager: AppManager? = null

        /**
         * 单一实例
         */
        val instance: AppManager
            get() {
                if (mAppManager == null) {
                    mAppManager = AppManager()
                }
                return mAppManager!!
            }
    }
    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    val topActivity: Activity
        get() = mActivityStack!!.lastElement()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (mActivityStack == null) {
            mActivityStack = Stack()
        }
        mActivityStack!!.add(activity)
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    fun killTopActivity() {
        val activity = mActivityStack!!.lastElement()
        killActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun killActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            mActivityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun killActivity(cls: Class<*>) {
        for (activity in mActivityStack!!) {
            if (activity.javaClass == cls) {
                killActivity(activity)
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun killAllActivity() {
        var i = 0
        val size = mActivityStack!!.size
        while (i < size) {
            if (null != mActivityStack!![i]) {
                mActivityStack!![i].finish()
            }
            i++
        }
        mActivityStack!!.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            killAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.restartPackage(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
        }

    }
}