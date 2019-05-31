package com.leen.kotlin_library.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.leen.kotlin_library.httpTools.HttpListener
import com.leen.kotlin_library.util.AppManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.leen.kotlin_library.event.ForceToLoginEvent
import com.leen.kotlin_library.event.MessageEvent
import com.leen.kotlin_library.helper.PermissionListener
import com.leen.kotlin_library.util.StringUtils
import com.leen.kotlin_library.util.logWarn
import me.yokeyword.fragmentation.SwipeBackLayout
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference
import java.util.ArrayList


/**
 * @author
 * 创建人：leen
 * 创建日期：2019/5/15
 * 描述：Activity基类
 *
 */
abstract class BaseAty: SwipeBackActivity(),View.OnClickListener,HttpListener{
    /**
     * 默认显示沉浸式，如果不使用，需要在onCreate方法之前设置为false
     */
    var changeStatusBar=true
    var loadingDialog:Dialog?=null
    var tipTextView:TextView?=null

    /**
     * 判断当前Activity是否在前台。
     */
    protected var isActive: Boolean = false

    /**
     * 当前Activity的实例。
     */
    protected var activity: Activity? = null
    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var badNetworkView: View? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var noContentView: View? = null
    private var mListener: PermissionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContentView(getLayoutResId())
        loadingDialog=createLoadingDialog(this,"数据加载中...")//创建loading
        initView()
        initData()
        AppManager.instance.addActivity(this)
        if (changeStatusBar) {
            immersionBar {
                autoStatusBarDarkModeEnable(true,0.2f)//自动设定状态栏字体颜色
            }
        }
        swipeBackLayout.setParallaxOffset(0.38f) // （类iOS）滑动退出视觉差，默认0.3
        swipeBackLayout.setEdgeOrientation(SwipeBackLayout.EDGE_LEFT)//划出方向
    }

    /**
     * 设置沉浸式状态栏颜色
     */
    fun setStatusBarColor(statusColor:Int){
        if (changeStatusBar){
            immersionBar {
                statusBarColor(statusColor)//设置状态栏颜色
                navigationBarColor(com.leen.kotlin_library.R.color.colorPrimary)//设置导航栏颜色
                autoStatusBarDarkModeEnable(true,0.2f)//自动设定状态栏字体颜色
                fitsSystemWindows(true)//解决状态栏与view重叠问题
                keyboardEnable(true)//解决软键盘与底部输入框冲突问题
            }
        }
    }
    /**
     * 设置页面
     */
    abstract fun getLayoutResId(): Int

    /**
     * 初始化
     */
    abstract fun initView()

    /**
     * 逻辑处理
     */
    abstract fun initData()


    override fun onFailure(requestUrl: String) {
        removeLoadingDialog()
        showToast("服务器错误，请稍候重试")
    }

    override fun onOverTime(map: Map<*, *>) {
        removeLoadingDialog()
        showToast(map.get("message").toString())
        val event = ForceToLoginEvent()
        EventBus.getDefault().post(event)
    }

    override fun onStarted() {
        showLoadingDialog("Loading...")
    }

    override fun onSuccess(requestUrl: String, jsonStr: String) {
        removeLoadingDialog()
    }
    protected fun showToast(text: String?) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setText(text)
        toast.show()
    }

    override fun onError(requestUrl: String, map: Map<*, *>) {
        removeLoadingDialog()
        showToast(map.get("message").toString())
    }
    fun createLoadingDialog(context: Context,msg:String):Dialog{
        val inflater=LayoutInflater.from(context)
        val v=inflater.inflate(com.leen.kotlin_library.R.layout.loading_dialog, null)
        val layout = v.findViewById(com.leen.kotlin_library.R.id.dialog_view) as LinearLayout
        val spaceshipImage = v.findViewById(com.leen.kotlin_library.R.id.img) as ImageView
        tipTextView = v.findViewById(com.leen.kotlin_library.R.id.tipTextView) as TextView// 提示文字
        // 加载动画
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, com.leen.kotlin_library.R.anim.loading_animation
        )
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation)
        tipTextView!!.text = msg// 设置加载信息

        val loadingDialog = Dialog(context, com.leen.kotlin_library.R.style.loading_dialog)// 创建自定义样式dialog
        loadingDialog.setCancelable(false)// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))// 设置布局
        return loadingDialog
    }
    fun showLoadingDialog(msg:String){
        if (!StringUtils.isEmpty(msg)){
            tipTextView!!.setText(msg)
        }
        loadingDialog!!.show()
    }
    fun removeLoadingDialog(){
        loadingDialog!!.dismiss()

    }
    /**
     * 检查和处理运行时权限，并将用户授权的结果通过PermissionListener进行回调。
     *
     * @param permissions
     * 要检查和处理的运行时权限数组
     * @param listener
     * 用于接收授权结果的监听器
     */
    protected fun handlePermissions(permissions: Array<String>?, listener: PermissionListener) {
        if (permissions == null || activity == null) {
            return
        }
        mListener = listener
        val requestPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(permission)
            }
        }
        if (!requestPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity!!, requestPermissionList.toTypedArray(), 1)
        } else {
            listener.onGranted()
        }
    }
    /**
     * 隐藏软键盘。
     */
    fun hideSoftKeyboard() {
        try {
            val view = currentFocus
            if (view != null) {
                val binder = view.windowToken
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            logWarn(TAG, e.message, e)
        }

    }

    /**
     * 显示软键盘。
     */
    fun showSoftKeyboard(editText: EditText?) {
        try {
            if (editText != null) {
                editText.requestFocus()
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(editText, 0)
            }
        } catch (e: Exception) {
            logWarn(TAG, e.message, e)
        }

    }


    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }
    override fun onDestroy() {
        super.onDestroy()
        activity = null
        //必须调用该方法，防止内存泄露，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再次进入将记忆最后一次bar改变的状态
    }
    companion object {

        private const val TAG = "BaseActivity"
    }
}