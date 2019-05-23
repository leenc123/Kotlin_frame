package com.leen.kotlin_library.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import com.gyf.immersionbar.ktx.immersionBar
import com.leen.kotlin_library.httpTools.HttpListener
import com.leen.kotlin_library.util.AppManager
import com.leen.kotlin_library.util.L
import me.yokeyword.fragmentation.SupportActivity
import android.widget.ImageView
import android.widget.TextView
import android.view.animation.AnimationUtils
import com.leen.kotlin_library.util.StringUtils


/**
 * @author
 * 创建人：leen
 * 创建日期：2019/5/15
 * 描述：Activity基类
 *
 */
abstract class BaseAty: SupportActivity(),View.OnClickListener,HttpListener{
    /**
     * 默认显示沉浸式，如果不使用，需要在onCreate方法之前设置为false
     */
    var changeStatusBar=true
    var loadingDialog:Dialog?=null
    var tipTextView:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
    override fun onDestroy() {
        super.onDestroy()
        //必须调用该方法，防止内存泄露，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再次进入将记忆最后一次bar改变的状态
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
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT))// 设置布局
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

}