package com.leen.model_mine.init

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.leen.kotlin_library.httpTools.FuelTestHelper
import com.leen.kotlin_library.util.AppManager
import com.leen.model_main.home.MainActivity
import com.leen.model_main.launch.RouteService
import com.leen.model_mine.R
import com.leen.model_mine.config.Config
import com.sjtu.yifei.route.Routerfit

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/5/16
 * 描述：app启动页
 *
 */
class SplashAty : Activity() {
    val instances by lazy { this }
    val handle = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg!!.what == 0x0001) {
                Routerfit.register(RouteService::class.java).launchMainActivity(1)
                finish()

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            transparentNavigationBar()
            hideBar(BarHide.FLAG_HIDE_BAR)
            fullScreen(true)
        }
        setContentView(R.layout.aty_splash)
        AppManager.instance.addActivity(this)
        //初始化拦截器
        FuelTestHelper.initFuel(Config.instance.baseUrl!!, "", Config.instance.timeoutInMillisecond)
        Thread(Runnable {
            kotlin.run {
                Thread.sleep(1000)
                var message = Message()
                message.what = 0x0001
                message.obj = '1'
                handle.sendMessage(message)
            }
        }).start()

    }
}