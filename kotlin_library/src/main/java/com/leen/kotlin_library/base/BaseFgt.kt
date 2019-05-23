package com.leen.kotlin_library.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.gyf.immersionbar.ktx.immersionBar
import com.leen.kotlin_library.R
import com.leen.kotlin_library.httpTools.HttpListener
import com.leen.kotlin_library.util.L
import com.leen.kotlin_library.util.StringUtils
import me.yokeyword.fragmentation.SupportFragment


/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/15
 * 描述：Fragment基类
 *
 */
abstract class BaseFgt :SupportFragment(), View.OnClickListener, HttpListener {
    var loadingDialog:Dialog?=null
    var tipTextView:TextView?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadingDialog=createLoadingDialog(this!!.context!!,"数据加载中...")//创建loading

    }

    fun initImmersionBar(statusColor:Int) {
        immersionBar {
            statusBarColor(statusColor)
            navigationBarColor(R.color.colorPrimary)//设置导航栏颜色
            autoStatusBarDarkModeEnable(true,0.2f)//自动设定状态栏字体颜色
            keyboardEnable(true)//解决软键盘与底部输入框冲突问题
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        immersionBar {
            statusBarColor(R.color.colortransparent)
            navigationBarColor(R.color.colorPrimary)//设置导航栏颜色
            autoStatusBarDarkModeEnable(true,0.2f)//自动设定状态栏字体颜色
            keyboardEnable(true)//解决软键盘与底部输入框冲突问题
        }
    }
    override fun onFailure(requestUrl: String) {
        showToast("服务器错误，请稍候重试")
    }

    override fun onOverTime(map: Map<*, *>) {
        L.i(Gson().toJson(map))
        showToast(map.get("message").toString())
    }

    override fun onStarted() {

    }

    override fun onSuccess(requestUrl: String, jsonStr: String) {
        L.i(jsonStr)
    }
    protected fun showToast(text: String?) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setText(text)
        toast.show()
    }

    override fun onError(requestUrl: String, map: Map<*, *>) {
        L.i(Gson().toJson(map))
        showToast(map.get("message").toString())
    }
    fun createLoadingDialog(context: Context, msg:String): Dialog {
        val inflater= LayoutInflater.from(context)
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

        val loadingDialog = Dialog(context, R.style.loading_dialog)// 创建自定义样式dialog
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