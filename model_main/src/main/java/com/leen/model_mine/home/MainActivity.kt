package com.leen.model_main.home

import android.app.Activity
import android.content.Intent
import android.content.ServiceConnection
import android.view.View
import com.cysion.targetfun._addTextChangedListener
import com.leen.kotlin_library.base.BaseAty
import com.leen.kotlin_library.systemBarUtil.KeyboardPatch
import com.leen.kotlin_library.util.MD5
import com.leen.model_mine.R
import com.leen.model_mine.home.FragmentAty
import com.leen.model_mine.http.HttpUser
import com.leen.model_mine.service.MyService
import com.sjtu.yifei.annotation.Route
import kotlinx.android.synthetic.main.aty_main.*
import android.os.IBinder
import android.content.ComponentName



@Route(path = "/model_main/main_activity")
class MainActivity : BaseAty() {
    var connection:ServiceConnection?=null
    var myBinder:MyService.MyBinder?=null
    var isBinder=false
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_fragment-> FragmentAty.startActivity(this)
            R.id.tv_http_get-> HttpUser.instance.login(listOf("verifyCode" to "u8p7","username" to "adminUser","userType" to "1","password" to MD5.encode("123456"),"random" to (System.currentTimeMillis().toString()),"appRegister" to "100d85590909e50a4e9"),this)
            R.id.btn_service-> {
                var intent=Intent(this,MyService::class.java)
                startService(intent)
            }
            R.id.btn_stop->{
                var intent=Intent(this,MyService::class.java)
                stopService(intent)
            }
            R.id.btn_download->{
                var intent=Intent(this,MyService::class.java)
                isBinder=bindService(intent,connection,BIND_AUTO_CREATE)
            }
            R.id.btn_stop_download->unbindService(connection)
        }
    }

    override fun getLayoutResId(): Int = R.layout.aty_main

    override fun initView() {
        setStatusBarColor(R.color.colorWhite)
        KeyboardPatch.patch(this, rel_statusBar)
        tv_fragment.setOnClickListener(this)
        tv_http_get.setOnClickListener(this)
        btn_service.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
        btn_download.setOnClickListener(this)
        btn_stop_download.setOnClickListener(this)
        setSwipeBackEnable(false) // 首页禁止手势滑动
    }

    override fun initData() {
        var sss=intent.getIntExtra("para2",0)
        showToast(sss.toString())
        editText._addTextChangedListener {
            _afterTextChanged {

            }
        }
        connection = object : ServiceConnection {

            override fun onServiceDisconnected(name: ComponentName) {}

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                myBinder = service as MyService.MyBinder
                myBinder!!.startDownload()
            }
        }

    }

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onSuccess(requestUrl: String, jsonStr: String) {
        super.onSuccess(requestUrl, jsonStr)
        showToast(jsonStr+"aaaaaaaaaaaaa")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (connection!=null&&isBinder){
            unbindService(connection)
        }
    }
}
