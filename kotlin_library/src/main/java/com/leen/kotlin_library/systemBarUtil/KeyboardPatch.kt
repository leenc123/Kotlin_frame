package com.leen.kotlin_library.systemBarUtil

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.OSUtils

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：沉浸式解决软键盘弹出和标题栏问题
 *
 */
class KeyboardPatch private constructor(
    private val mActivity: Activity,
    private val mContentView: View = mActivity.findViewById(android.R.id.content)
) {
    private var mDecorView: View? = null
    private var flag = false

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val r = Rect()
        mDecorView!!.getWindowVisibleDisplayFrame(r) //获取当前窗口可视区域大小的
        val height = mDecorView!!.context.resources.displayMetrics.heightPixels //获取屏幕密度，不包含导航栏
        val diff = height - r.bottom
        if (diff > 0) {
            if (mContentView.paddingBottom != diff) {
                if (flag || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()
                    || !ImmersionBar.with(mActivity).getBarParams().navigationBarEnable
                ) {
                    mContentView.setPadding(0, 0, 0, diff)
                } else {
                    mContentView.setPadding(0, 0, 0, diff + ImmersionBar.getNavigationBarHeight(mActivity))
                }
            }
        } else {
            if (mContentView.paddingBottom != 0) {
                if (flag || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()
                    || !ImmersionBar.with(mActivity).getBarParams().navigationBarEnable
                ) {
                    mContentView.setPadding(0, 0, 0, 0)
                } else {
                    mContentView.setPadding(0, 0, 0, ImmersionBar.getNavigationBarHeight(mActivity))
                }
            }
        }
    }

    init {
        this.mDecorView = mActivity.window.decorView
        if (mContentView == mActivity.findViewById(android.R.id.content)) {
            this.flag = false
        } else {
            this.flag = true
        }
    }

    @JvmOverloads
    fun enable(mode: Int = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) {
        mActivity.window.setSoftInputMode(mode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDecorView!!.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)//当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时，所要调用的回调函数的接口类
        }
    }

    @JvmOverloads
    fun disable(mode: Int = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) {
        mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDecorView!!.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        }
    }

    companion object {

        /**
         * Patch keyboard patch.
         *
         * @param activity the activity
         * @return the keyboard patch
         */
        fun patch(activity: Activity): KeyboardPatch {
            return KeyboardPatch(activity)
        }

        /**
         * Patch keyboard patch.
         *
         * @param activity    the activity
         * @param contentView 界面容器，指定布局的根节点
         * @return the keyboard patch
         */
        fun patch(activity: Activity, contentView: View): KeyboardPatch {
            return KeyboardPatch(activity, contentView)
        }
    }

}
/**
 * 监听layout变化
 */
/**
 * 取消监听
 */
