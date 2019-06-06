package com.leen.model_mine.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.leen.kotlin_library.util.L

/**
 * @author
 * 创建人：Administrator
 * 创建日期：2019/6/5
 * 描述：
 *
 */
class MyService :Service() {
    val TAG="MyService"
    var myBinder:MyBinder= MyBinder()
    override fun onCreate() {
        super.onCreate()
        L.e(TAG,"onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread(object :Runnable{
            override fun run() {
                //开始执行后台任务
            }
        }).start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    override fun onDestroy() {
        L.e(TAG,"onDestroy()")
        super.onDestroy()
    }
    class MyBinder:Binder(){
        fun startDownload(){
            Thread(object :Runnable{
                override fun run() {
                    for (i in 0..100){
                        Thread.sleep(1000)
                        L.e("MyService","下载进度:${i}")
                    }
                }
            }).start()
        }
    }
}