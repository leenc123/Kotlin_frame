package com.leen.kotlin_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.leen.kotlin_demo.http.HttpUser
import com.leen.kotlin_library.base.BaseAty
import com.leen.kotlin_library.systemBarUtil.KeyboardPatch
import com.leen.kotlin_library.util.MD5
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAty() {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_fragment->FragmentAty.startActivity(this)
            R.id.tv_http_get-> HttpUser.instance.login(listOf("verifyCode" to "u8p7","username" to "adminUser","userType" to "1","password" to MD5.encode("123456"),"random" to (System.currentTimeMillis().toString()),"appRegister" to "100d85590909e50a4e9"),this)
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView() {
        setStatusBarColor(R.color.colorWhite)
        KeyboardPatch.patch(this, rel_statusBar)
        tv_fragment.setOnClickListener(this)
        tv_http_get.setOnClickListener(this)
    }

    override fun initData() {

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
}
